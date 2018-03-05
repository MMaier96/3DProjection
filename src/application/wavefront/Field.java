package application.wavefront;

public class Field {
	private int index1;
	private int index2;
	private int index3;
	private int index4;
	boolean index4set;

	public Field(int index1, int index2, int index3) {
		this.index1 = index1;
		this.index2 = index2;
		this.index3 = index3;
		index4set = false;
	}

	public Field(int index1, int index2, int index3, int index4) {
		this.index1 = index1;
		this.index2 = index2;
		this.index3 = index3;
		this.index4 = index4;
		index4set = true;
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
