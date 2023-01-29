public class PreLab7 {

	public static void main(String[] args) {

		int Row;
		int Blanks = 4;
		int Stars = 5;
		for( Row = 1; Row <= 5; Row++ )
		{
			for(Blanks = 4; Blanks >= Row; Blanks--)
				System.out.print(' ');
				
			for(Stars = 1; Stars <= ((2 * Row) - 1); Stars++ )
				System.out.print('*');
					
			System.out.println();
			
		}
	

	}

}