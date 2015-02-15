public class Star {

	/**
	 * @param args
	 */
	int xlocation=0;
	int ylocation=0;
	float transparency=0;
	float increment=-0.01f;
	
	
	public Star(int x,int y,float a) {
		xlocation=x;
		ylocation=y;
		transparency=a;
	}
	
	public Star(int x,int y){
		xlocation=x;
		ylocation=y;
	}

}