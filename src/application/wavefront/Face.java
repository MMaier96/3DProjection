package application.wavefront;

public class Face {
	private int index1;
	private int index2;
	private int index3;
	private int index4;
	boolean index4set;

	public Face(int index1, int index2, int index3) {
		this.index1 = index1;
		this.index2 = index2;
		this.index3 = index3;
		index4set = false;
	}

	public Face(int index1, int index2, int index3, int index4) {
		this.index1 = index1;
		this.index2 = index2;
		this.index3 = index3;
		this.index4 = index4;
		index4set = true;
	}

	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public int getIndex2() {
		return index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}

	public int getIndex3() {
		return index3;
	}

	public void setIndex3(int index3) {
		this.index3 = index3;
	}

	public int getIndex4() {
		return index4;
	}

	public void setIndex4(int index4) {
		this.index4 = index4;
	}

	public boolean isIndex4set() {
		return index4set;
	}

	public void setIndex4set(boolean index4set) {
		this.index4set = index4set;
	}

	@Override
	public String toString() {
		String field = "";
		field = "f " + index1 + " " + index2 + " " + index3;
		if (index4set) {
			field += " " + index4;
		}
		return field;
	}
}
