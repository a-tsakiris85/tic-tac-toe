
public enum Piece {
	
		RED {
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "R";
			}
		}, YELLOW {
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "Y";
			}
		}, EMPTY {
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "-";
			}
		};
		
		
		public abstract String toString();
	
}
