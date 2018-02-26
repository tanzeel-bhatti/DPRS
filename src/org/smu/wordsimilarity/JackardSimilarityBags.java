
package org.smu.wordsimilarity;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.lums.serl.dprs.Node;
import com.lums.serl.dprs.Node.TYPE;

import cc.mallet.util.Constants;
import dprs.views.SampleView;


public class JackardSimilarityBags {
	static List<ArrayList<String>> projectsKeywordsCollection = new ArrayList<ArrayList<String>>();
	static ArrayList<String> featureProjectIDs = new ArrayList<String>();
	//static String inputFile = System.getProperty("user.dir")+"F://SEWordSim-r1.db";
	static String inputFile = Constants.SEWordSimPath;
	
	static WordSimDBFacade facade = new WordSimDBFacade(inputFile);
	static ArrayList<String> resultsDisplay = new ArrayList<String>();
	static PrintWriter writer;
	static Node root = null;

	public void findRecommendations(ArrayList<String> userFeatureVector) throws Throwable
	{
		root = new Node("Recommentations", TYPE.ROOT);
		Sample s1 = new Sample();
		projectsKeywordsCollection.clear();
		if(Constants.keyWordstoGet.contentEquals("StemmedName"))
		{
			ArrayList<String> stemmedUserFeatureVector = s1.generateEnhancedFeatureVector(userFeatureVector); //stemmed user feature vector
					
			projectsKeywordsCollection.add(stemmedUserFeatureVector);
		
		}
		else
		{
			projectsKeywordsCollection.add(userFeatureVector);
		}

		featureProjectIDs.clear();
		featureProjectIDs.add("");

		getFeatureVectorsFromDB(); //get all the feature vectors from db based on project ids
		//addTemporary();

		System.out.println(projectsKeywordsCollection);
		System.out.println(featureProjectIDs);
		int totalMethods = projectsKeywordsCollection.size();
		
		
		System.out.println("Total methods: "+ totalMethods);
		
		String filename = "";
		if(userFeatureVector.size() < Constants.numberKeywordsThresh) {
			filename = Constants.recommendationsOutputPath.replaceAll(".txt", "_inadequate.txt");			
		}else {
			filename = Constants.recommendationsOutputPath;			
			
		}
			writer = new PrintWriter(filename, "UTF-8");
		
		System.out.println("\nTopics extracted from your code:" + userFeatureVector);
		
		writer.println("\r\nTopics extracted from your code:" + userFeatureVector);
		//int size = resultsDisplay.size();
		//for(int i=0;i<resultsDisplay.size();i++){
		//    writer.println(resultsDisplay.get(i));
		//} 
		
		/**** Jaccard Similarity Recommendations *****/
		createDistanceMatrix(projectsKeywordsCollection);
		
		writer.println("==END==");
		writer.close();
		System.out.println("==Done==");
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {

				SampleView view = (SampleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("dprs.views.SampleView");
				if (view != null) {
					Node n = new Node("", null);
					n.addChild(root);
					view.setInput(n);
				}
				
			}
		});
//		DemoJFileChooser.setStatusLabel("Done!");
		
		/**** SEWordSim Recommendations *****/
		//compareFeatureVectorsUsingSEWordSim();
		
		//calculateShannonEntropy(featureVectors.get(0));
	}
	
	public static Double calculateShannonEntropy(List<String> values) {
		  Map<String, Integer> map = new HashMap<String, Integer>();
		  // count the occurrences of each value
		  for (String sequence : values) {
		    if (!map.containsKey(sequence)) {
		      map.put(sequence, 0);
		    }
		    map.put(sequence, map.get(sequence) + 1);
		  }
		 
		  // calculate the entropy
		  Double result = 0.0;
		  for (String sequence : map.keySet()) {
		    Double frequency = (double) map.get(sequence) / values.size();
		    result -= frequency * (Math.log(frequency) / Math.log(2));
		  }
		 
		  return result;
	}

	private void compareFeatureVectorsUsingSEWordSim() throws Throwable {
		System.out.println("\n---------------- *** Top " + Constants.noOfRecommendations + " Recommendations using SEWordSim *** ----------------");
		double similarityScores[][] = new double[10][10];
		double projectSimilarityScore[] = new double[projectsKeywordsCollection.size()];
		String projectIDs[] = new String[projectsKeywordsCollection.size()];
		
		List<String> userFV = projectsKeywordsCollection.get(0);
		projectSimilarityScore[0] = 0;
		projectIDs[0] = "0";
		
		for (int i=1; i<projectsKeywordsCollection.size(); i++)
		{
			List<String> repoFV = projectsKeywordsCollection.get(i);
			for (int j = 0; j < userFV.size(); j++) {
				String userTopic = userFV.get(j);
				for (int k = 0; k < repoFV.size(); k++) {
					String repoTopic = repoFV.get(k);
					try{
					
					if (userTopic.equalsIgnoreCase(repoTopic))
					{
						similarityScores[j][k] = 10; // in order to boost our exactly matching keywords
					}
					else
					{
						similarityScores[j][k] = facade.computeSimilarity(userTopic, repoTopic);
					}
					}
					catch(Exception ex)
					{
						System.out.println("i: " + i + " j: " + j + " k: " + k + userTopic + repoTopic);
					}
				}
			}
			projectSimilarityScore[i] = 0;
			projectIDs[i] = featureProjectIDs.get(i);
			
			for (int a=0; a<10; a++)
			{
				for (int b=0; b<10; b++)
				{
					projectSimilarityScore[i] = projectSimilarityScore[i] + similarityScores[a][b];
				}
			}
		}
		
		for (int i=0; i<projectSimilarityScore.length; i++)
		{
			System.out.print(projectSimilarityScore[i] + "    ");
		}
		
		String[] topProjects = findMax(projectSimilarityScore, Constants.noOfRecommendations);
		
		for (int x=0; x < Constants.noOfRecommendations; x++)
		{
			System.out.println("\n--------------------------- Project No. "+ (x+1) +"---------------------------");
			System.out.println("\nRecommended Project ID: " + topProjects[x]);
			writer.println("\r\n--------------------------- Project No. "+ (x+1) +"---------------------------");
			writer.println("\r\nRecommended Project ID: " + topProjects[x]);
			recommendProject(topProjects[x], null);
		}
	}

	private static void addTemporary() {
		//get all the feature vectors from db based on project ids
		ArrayList<String> userFeatureVector2 = new ArrayList<String>();
		userFeatureVector2.add("print");
		userFeatureVector2.add("system");
		userFeatureVector2.add("spellcheck");
		userFeatureVector2.add("style");
		userFeatureVector2.add("matrix");

		ArrayList<String> userFeatureVector3 = new ArrayList<String>();
		userFeatureVector3.add("system");
		userFeatureVector3.add("editor");
		userFeatureVector3.add("translator");
		userFeatureVector3.add("print");
		userFeatureVector3.add("write");

		projectsKeywordsCollection.add(userFeatureVector2);
		projectsKeywordsCollection.add(userFeatureVector3);
	}

	private static void getFeatureVectorsFromDB() throws Throwable {
		DbConnection dc = new DbConnection();
		dc.openConnection();
		ArrayList<Integer> projectIDs = dc.getProjectIDOfDomainTopics();
		for(Integer pID: projectIDs )
		{
			//ResultSet projectWiseDomainTopics = dc.getDomainTopicsofProjectID(projectIDs.getInt("ProjectID"));
			ArrayList <String> projectDomainKeywords = dc.getKeywordsForProjectIDs(pID);
			
			projectsKeywordsCollection.add(projectDomainKeywords);
			featureProjectIDs.add(pID.toString());
		}
		dc.closeConnection();
	}
	
	public static void createDistanceMatrix(List<ArrayList<String>> projectsKeywordsCollection ) throws Throwable{
		
		System.out.println("\n---------------- *** Top " + Constants.noOfRecommendations + " Recommendations using Jaccard Similarity *** ----------------");
		writer = new PrintWriter(Constants.recommendationsOutputPath, "UTF-8");		 
		writer.println("\r\n---------------- *** Top " + Constants.noOfRecommendations + " Recommendations *** ----------------");
		
		int numberOfProjects = projectsKeywordsCollection.size()-1;
		double projectDistances[] = new double[numberOfProjects+1]; //Contains Distance of user project with the rest of the projects		
		//projectDistances[0]=2.0;
		int minDistanceIndex=0;
		
		for (int j=0; j<projectDistances.length; j++){
			if (j==0) 
				projectDistances[j]=2.0;
			else {
			projectDistances[j] = calculateJaccardDifference(projectsKeywordsCollection.get(0), projectsKeywordsCollection.get(j));
			}
			if (projectDistances[j]<projectDistances[minDistanceIndex] && projectDistances[j]!=2)
				minDistanceIndex = j;
		}	
		
		double roundedProjectDistances[] = new double[projectDistances.length];
		
		for(int j=0; j<projectDistances.length; j++){
		
			System.out.print(Math.round(projectDistances[j]*100.0)/100.0 + "	");
			roundedProjectDistances[j] = round(projectDistances[j],2);
		}
		
		String[] topProjects = getRecommendedSortedProjectIDs(roundedProjectDistances);

		//System.out.println("\nmin distant node of  node 0: "+ minD[0]);
		System.out.println("Recommended Project ID from "+ topProjects.length + " projects: " + featureProjectIDs.get(minDistanceIndex));
		 
		writer.println("Recommended Project ID from "+ topProjects.length + " projects: " + featureProjectIDs.get(minDistanceIndex));
		//recommendProject(featureProjectIDs.get(minD[0]));
		
		for (int x=0; x < topProjects.length; x++)
		{
			System.out.println("\n--------------------------- Project No. "+ (x+1) +"---------------------------");
			System.out.println("\nRecommended Project ID: " + topProjects[x]);
			writer.println("\r\n--------------------------- Project No. "+ (x+1) +"---------------------------");
			writer.println("\r\nRecommended Project ID: " + topProjects[x]);
			recommendProject(topProjects[x], roundedProjectDistances);
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	private static String[] getRecommendedSortedProjectIDs(final double[] roundedProjectDistances) {
	
		//sort roundedProjectDistances to get the sorted ascending sequence in a third array idxs		
		Integer[] idxs = new Integer[featureProjectIDs.size()];
		for(int i = 0; i < featureProjectIDs.size(); i++) idxs[i] = i;
		Arrays.sort(idxs, new Comparator<Integer>(){
		    public int compare(Integer o1, Integer o2){
		    return Double.compare(roundedProjectDistances[o1], roundedProjectDistances[o2]);
		    }
		});	
	
		int numberOfProjectsAboveThreshold = 0;
		
		for (int i=0; i < featureProjectIDs.size(); i++){
			double projectScore = 1-roundedProjectDistances[i]; 
			if(projectScore >= Constants.threshold )
				numberOfProjectsAboveThreshold+=1;
				
			
		}
		
		String[] sortedProjectIDs = new String[featureProjectIDs.size()];
		for (int i=0; i < featureProjectIDs.size(); i++){
			
			sortedProjectIDs[i] = featureProjectIDs.get(idxs[i]);
		}
		String[] recommendedProjectIDs;
		if(Constants.noOfRecommendations != 0 && Constants.noOfRecommendations <= numberOfProjectsAboveThreshold)
		{
			recommendedProjectIDs = new String[Constants.noOfRecommendations];
			for (int i=0; i < Constants.noOfRecommendations; i++){
				
				recommendedProjectIDs[i] = sortedProjectIDs[i];
			}
		}
		else
		{
			recommendedProjectIDs = new String[numberOfProjectsAboveThreshold];
			for (int i=0; i < numberOfProjectsAboveThreshold; i++){
				//double projectScore = 1-roundedProjectDistances[i]; 
				//if(projectScore > Constants.threshold)
				//	break;
				recommendedProjectIDs[i] = sortedProjectIDs[i];
			}
		}
		
		return recommendedProjectIDs;
	}

	
	
	private static String[] findMax(double[] array, int top_k) {
	    double[] max = new double[top_k];
	    int[] maxIndex = new int[top_k];
	    String[] returnIndex = new String[top_k];
	    
	    Arrays.fill(max, Double.NEGATIVE_INFINITY);
	    Arrays.fill(maxIndex, -1);

	    top: for(int i = 0; i < array.length; i++) {
	        for(int j = 0; j < top_k; j++) {
	            if(array[i] > max[j]) {
	                for(int x = top_k - 1; x > j; x--) {
	                    maxIndex[x] = maxIndex[x-1]; max[x] = max[x-1];
	                }
	                maxIndex[j] = i; max[j] = array[i];
	                continue top;
	            }
	        }
	    }
	    int x=0;
	    for (int a=0; a<3; a++)
	    {
	    	//System.out.println(max[a] + "at index " + maxIndex[a]);
	    	returnIndex[x] = featureProjectIDs.get(maxIndex[a]);
	    	x++;
	    }
	    return returnIndex;
}

	private static void recommendProject(String projectID, double differences[]) throws Throwable {
		if (projectID!=null && projectID!= "" && projectID!="0" && !projectID.isEmpty())
		{
			DbConnection dc = new DbConnection();
			dc.openConnection();
			System.out.println("Recommended project's location is: " + dc.getProjectName(projectID));
			writer.println("Recommended project's uri is: " + dc.getProjectName(projectID));
			
			showProjectInfo(projectID, differences, dc);
			String pn = dc.getProjectName(projectID);
			String[] segments = pn.split("\\\\");
			String projectName = segments[segments.length-1];
			// Create a Project Name Node
			Node s = new Node(projectName, TYPE.PROJECT);
			// Populate Node with Project's similarity score
			double score = 1 - differences[featureProjectIDs.indexOf(projectID)];
			s.setScore(Math.round((1.0-score)*100.0));
			// Populate the Category of the project
			s.setCategory(dc.getCategoryProject(projectID));
			// Populate the Description of the project
			s.setDescription(dc.getDescProject(projectID));
			// Populate the Topics of the project
			s.setTopics(dc.getKeywordsForProjectIDs(Integer.parseInt(projectID)));
			root.addChild(s);
			writer.flush();
			showPatterns(projectID, dc);
			writer.flush();
			showPatternInstances(projectID, dc, s);
			writer.flush();
			dc.closeConnection();
		}
		else
		{
			System.out.println("No useful recommendation found...");
			writer.println("No useful recommendation found...");
		}
	}


	private static void showProjectInfo(String projectID, double[] differences, DbConnection dc) throws SQLException {
		String pn = dc.getProjectName(projectID);
		String[] segments = pn.split("\\\\");
		String idStr = segments[segments.length-1];
		String idStr2 = segments[segments.length-2];
		double score = 1 - differences[featureProjectIDs.indexOf(projectID)];
		System.out.println("Recommended project's name is: " + idStr);
		System.out.println("Recommended project's score is: " + score);
		System.out.println("Recommended project's location is: " + idStr2 + "\\" + idStr);
		System.out.println("Recommended project's category is: " + dc.getCategoryProject(projectID));
		System.out.println("Recommended project's description is: " + dc.getDescProject(projectID));
		//System.out.println("Topics of this project are: " +featureVectors.get(featureProjectIDs.indexOf(projectID)));
		writeProjectInfo(projectID, dc, idStr, idStr2, score);
	}

	private static void writeProjectInfo(String projectID, DbConnection dc, String idStr, String idStr2, double score) throws SQLException {
		String target = "Projects for Repo Building";
		String replacement = "Design Patterns Repository";
		if(idStr2.equalsIgnoreCase(target))
			idStr2 = idStr2.replace(target, replacement);
		writer.println("\r\nRecommended project's name is: " + idStr);
		writer.println("\r\nRecommended project's score is: " + (Math.round((1.0-score)*100.0)/100.0));
		writer.println("\r\nRecommended project's location is: \\\\sus\\Software\\Freeware\\WorkSpace\\" + idStr2 + "\\" + idStr);
		writer.println("\r\nRecommended project's category is: " + dc.getCategoryProject(projectID));
		writer.println("\r\nRecommended project's description is: " + dc.getDescProject(projectID));
		//writer.println("\r\nTopics of this project are: " +featureVectors.get(featureProjectIDs.indexOf(projectID)));
		ArrayList <String> projectWiseDomainTopics = dc.getKeywordsForProjectIDs(Integer.parseInt(projectID));
		writer.println("\r\nTopics of this project are: ");
		System.out.println("\r\nTopics of this project are: ");
		for (String keyword: projectWiseDomainTopics)
		{
			writer.append(keyword + " ");
			System.out.print(keyword + " ");
		}
		System.out.println("\n");
		writer.println("\r\n");
		//writer.println("\r\nPatterns implemented are: \n\r");
		//writer.println("\n\r");
	}
	
	private static void showPatterns(String projectID, DbConnection dc) throws SQLException {
		System.out.println("Patterns implemented are: ");
		ResultSet patterns = dc.getPatternsOfProject(projectID);
		while(patterns.next()){
			System.out.println(patterns.getString(1));
			//System.out.println(patterns.getString(1) + ": " + patterns.getString(2));
			//writer.println(patterns.getString(1));
		}
		patterns.beforeFirst();
		writePatterns(patterns);
	}

	private static void writePatterns(ResultSet patterns) throws SQLException {
		writer.println("Patterns implemented are: ");
		while(patterns.next()){
			writer.println(patterns.getString(1));
		}
	}

	private static void showPatternInstances(String projectID, DbConnection dc, Node parent) throws SQLException {
		ResultSet patternIDs = dc.getPatternIDsOfProject(projectID);
		System.out.println("\nDetails of project are: ");
		while(patternIDs.next()){
			Node p = new Node(patternIDs.getString(2), TYPE.DP);
			parent.addChild(p);
			System.out.println("\nPattern Name: " + patternIDs.getString(2));
			ResultSet patternIDsDetails = dc.getPatternIDsInstances(projectID,patternIDs.getString(1));
			
			
			while(patternIDsDetails.next()){
				String detail = patternIDsDetails.getString(1);
				//////////////////OLD
//				Node c = new Node(detail, TYPE.INSTANCE);
//				p.addChild(c);
				/////////////////OLD
				
				System.out.println("\nPattern instance "+patternIDsDetails.getString(2)+" is:");
				Node instanceNode = new Node("Pattern instance " + patternIDsDetails.getString(2), TYPE.INSTANCE);
				p.addChild(instanceNode);
				
				String[] metaData = detail.split("\\|");
				String packageName = "";
				String className = "";
				String designPatternPath = "";
				String directory = patternIDsDetails.getString(3);
				String[] splitPackageName;
				for (int i=0; i<metaData.length-1; i++)
				{
					if (!(metaData[i].contains("()")))
					{
						packageName = (metaData[i].split(":"))[1];
						String[] names = null;
						if (packageName.contains(" "))
						{
							names = packageName.split(" ");
							for(String name:names)
							{
								if (name.contains("."))
								{
									packageName = name;
								}
							}
						}
						splitPackageName = packageName.split("\\.");
						className = splitPackageName[splitPackageName.length - 1];
						className = (className.split("\\$"))[0];
						designPatternPath = DesignPatternPathExtractor.findPath(className.trim(),directory);
						System.out.println("Class Name/Role Name: " + (metaData[i].split(":"))[1] + "/ " + (metaData[i].split(":"))[0]);
						System.out.println("DesignPatternPath: " + designPatternPath);
						Node classNode = new Node(metaData[i], TYPE.CLASS);
						classNode.setSourcePath(designPatternPath);
						instanceNode.addChild(classNode);
					}
					else
					{
						//FIXME ///////////////////////////////
						packageName = (metaData[i].split(":"))[1];
						String[] names = null;
						if (packageName.contains(" "))
						{
							names = packageName.split(" ");
							for(String name:names)
							{
								if (name.contains("."))
								{
									packageName = name;
								}
							}
						}
						splitPackageName = packageName.split("\\.");
						className = splitPackageName[splitPackageName.length - 1];
						className = (className.split("\\$"))[0];
						designPatternPath = DesignPatternPathExtractor.findPath(className.trim(),directory);
						///////////////////////////////
						Node methodNode = new Node((metaData[i].split(":")[3]), TYPE.METHOD);
						methodNode.setSourcePath(designPatternPath);
						instanceNode.addChild(methodNode);
						System.out.println("Method Name: " + (metaData[i].split(":"))[3]);
					}
				}
				
				//String detail1 = detail.replace('|', '\n');
				//System.out.println("Pattern instance "+patternIDsDetails.getString(2)+" is: \n"+ detail1);
				//String[] detail2 = patternIDsDetails.getString(1).split("\\|"); 
				//System.out.println("Role Name: " + patternIDsDetails.getString("roleName") + ", Method Name: " + patternIDsDetails.getString("methodName") + ", Class Name: " + patternIDsDetails.getString("className"));
			}
		}
		writePatternInstances(projectID, dc);
		
		/*
		System.out.println("\nDetails of project are: ");
		ResultSet details = dc.getProjectDetails(projectID);
		while(details.next()){
			System.out.println("Role Name: " + details.getString("roleName") + ", Method Name: " + details.getString("methodName") + ", Class Name: " + details.getString("className"));
		}
		*/
	}

	private static void writePatternInstances(String projectID, DbConnection dc) throws SQLException {
		ResultSet patternIDs = dc.getPatternIDsOfProject(projectID);
		writer.println("\r\nDetails of project are: ");
		while(patternIDs.next()){
			writer.println("\r\nFor Design Pattern: " + patternIDs.getString(2));
			ResultSet patternIDsDetails = dc.getPatternIDsInstances(projectID,patternIDs.getString(1));
			
			while(patternIDsDetails.next()){
				String detail = patternIDsDetails.getString(1);
				String detail1 = detail.replace('|', '\n');
				String[] detail2 = patternIDsDetails.getString(1).split("\\|"); 
				writer.println("Pattern instance "+patternIDsDetails.getString(2)+" is: \r\n");
				for (String s: detail2) {
					writer.println(s); 
			    }
//				System.out.println("Role Name: " + patternIDsDetails.getString("roleName") + ", Method Name: " + patternIDsDetails.getString("methodName") + ", Class Name: " + patternIDsDetails.getString("className"));
			}
		}
	}

	public static double calculateJaccardDifference(List<String> list1, List<String> list2){
		double jaccardDifference = 1- calculateJaccardSimilarity(list1, list2);
		return jaccardDifference;
	}

	public static double calculateJaccardSimilarity(List<String> list1, List<String> list2){
		List<String> unions = new ArrayList<String>();
		List<String> intersections = new ArrayList<String>();
		double similarity;
		unions = findUnion(list1, list2);
		/*
		System.out.print("Union: ");
		for (int i=0; i<unions.size(); i++){
			System.out.print(unions.get(i) + '\t');
		}
		System.out.println();
		*/
		// Intersection of 1st two methods	 
		intersections = findIntersection(list1, list2);
		/*
		System.out.print("Intersection: ");
		for (int i=0; i<intersections.size(); i++){
			System.out.print(intersections.get(i) + '\t');
		}
		System.out.println();
		*/
		similarity = jaccardSimilarity(intersections, unions);
		//System.out.println("Similarity: " + similarity);
		return similarity;
	}

	public static <T> List<T> findUnion(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();
		set.addAll(list1);
		set.addAll(list2);
		return new ArrayList<T>(set);
	}

	public static <T> List<T> findIntersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();
		for (T t : list1) {
			if(list2.contains(t)) {
				list.add(t);
			}
		}
		return list;
	}

	public static <T> double jaccardSimilarity(List<T> list1, List<T> list2){
		float intersectionCount = list1.size();
		float unionCount = list2.size();
		return intersectionCount/unionCount;
	}
	
}