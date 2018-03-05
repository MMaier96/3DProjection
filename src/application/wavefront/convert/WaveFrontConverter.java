package application.wavefront.convert;

import application.wavefront.Group3D;

public abstract class WaveFrontConverter implements IWaveFrontConverter{
	
	protected Group3D group;
	
	public WaveFrontConverter(Group3D group) {
		this.group = group;
	}
	
	public abstract Object getResult();
	
}
