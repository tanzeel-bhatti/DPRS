package dprs.views;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;

import com.lums.serl.dprs.Node;

public class ProjectScoreDecorator extends LabelProvider implements ILightweightLabelDecorator {

	@Override
	public void decorate(Object arg0, IDecoration arg1) {
		if (!(arg0 instanceof Node))
			return;
		arg1.addSuffix(" [" + String.valueOf(((Node)arg0).getScore()) + "]");
		
	}
	

}
