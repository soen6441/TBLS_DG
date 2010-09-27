public class ClassNumber {
public int calculateClassNumWithinInitialTile (double x, double y, double r, int k, double Max_range)
{
	/*x= x/Max_range;
	y = y/Max_range;*/
//	System.out.println("X: "+x+ " Y:"+y);
	// values x and y are normalized and are between 0 and 1
	// r is the quasi factor between 0 and 1 (1 for UDG)
	// k is the tile length in number of hexagons
	int classNum=0;
	boolean found = false;
	double sqrt3= Math.sqrt(3);
	for (int i=1; i<=k; i++){
		for (int j=1; j<=k; j++){
			if (x >=((i-2)*(sqrt3/4)*r+(sqrt3/2)*r*(j-1)))
				if (x<(i*(sqrt3/4)*r+(sqrt3/2)*r*(j-1)))
					if (y<=((x/sqrt3)+(j-2)*(-0.5)*r+(i-1)*(0.5)*r))
						if (y>((x/sqrt3)+j*(-0.5)*r+(i-1)*(0.5)*r))
							if (y>=((-x/sqrt3)-(0.5)*r+(j-1)*(0.5)*r+(i-1)*r))
								if (y<((-x/sqrt3)+j*(0.5)*r+(i-1)*r)){
									found =true;
									classNum= (i-1)*k+j;
									break;
								}
		/*if ((i==3)&&(j==1)){
			System.out.println("i: "+i +" j: "+j);
			System.out.println("x >=((i-2)*(sqrt3/4)*r+(sqrt3/2)*r*(j-1))? x= "+x+" y= "+y +" Expr= "+((i-2)*(sqrt3/4)*r+(sqrt3/2)*r*(j-1)));
			System.out.println("x<(i*(sqrt3/4)*r+(sqrt3/2)*r*(j-1)))? x= "+x+" y= "+y +" Expr= "+(i*(sqrt3/4)*r+(sqrt3/2)*r*(j-1)));
			System.out.println("y<=((x/sqrt3)+(j-2)*(-1/2)*r+(i-1)*(1/2)*r))? x= "+x+" y= "+y +" Expr= "+((x/sqrt3)+(j-2)*(-1/2)*r+(i-1)*(1/2)*r));
			System.out.println("y>((x/sqrt3)+j*(-1/2)*r+(i-1)*(1/2)*r))? x= "+x+" y= "+y +" Expr= "+((x/sqrt3)+j*(-1/2)*r+(i-1)*(1/2)*r));
			System.out.println("y>=((-x/sqrt3)-(1/2)*r+(j-1)*(1/2)*r+(i-1)*r))? x= "+x+" y= "+y +" Expr= "+((-x/sqrt3)-(1/2)*r+(j-1)*(1/2)*r+(i-1)*r));
			System.out.println("y<((-x/sqrt3)+j*(1/2)*r+(i-1)*r))? x= "+x+" y= "+y +" Expr= "+((-x/sqrt3)+j*(1/2)*r+(i-1)*r));
			System.out.println("*************************************************************");
			System.out.println(((x/sqrt3)+(j-2)*(-0.5)*r+(i-1)*(0.5)*r)+"");
			System.out.println("r: "+r);
			System.out.println("(x/sqrt3)+(j-2)*(-1/2)*r="+ (x/sqrt3)+(j-2)*(-0.5)*r);
			System.out.println("(i-1)*(1/2)*r= "+((i-1))*(0.5)*r);
			System.out.println("i: "+i);
		}*/if (found)
				break;
		}
	}
	return classNum;
}
public int calculateClassNum (double x, double y, double max_range, int k, double r ){
	double sqrt3= Math.sqrt(3);
	x= x/max_range;
	y = y/max_range;
//	System.out.println("normalized x and y: "+x+";"+y);
//	System.out.println("normalized x : r*(sqrt3/4)"+(x*4.0/(r*sqrt3))+", y:r/2* "+y*2.0/r);
	
	double x_shift_unit= k*((sqrt3/2)*r);
	double y_shift_unit= (3*k)*(0.5*r);
	//shift to the right so that it falls between the max and min x values in the initial hexagon
	int num_of_x_shifts =0;
	double max_x= k*sqrt3*0.5*r+ (k-2)*sqrt3*0.25*r;
	
	while (x>= max_x){
		x-=x_shift_unit;
		num_of_x_shifts++;
	}
//	System.out.println("number of shifts toleft: "+num_of_x_shifts+" and new x is: "+x);
	//shift down by y_shift_unit until it is below the zig_zag line
	double temp_x= x;
	while (temp_x> (sqrt3*0.25*r))
		temp_x-=sqrt3*0.5*r;
//	System.out.println(" temp_x is "+temp_x);
	boolean is_above=true;
	while (is_above){
	if (temp_x<0){
	//	System.out.println(" temp_x is negative");
	//	System.out.println(" (-x/sqrt3)+(3*k-1)*r*0.5) is "+(-x/sqrt3)+(3*k-1)*r*0.5);
	//	System.out.println("k is: "+k);
		if (y<((-temp_x/sqrt3)+(3*k-1)*r*0.5))
			is_above=false;}
	else if (y<=((temp_x/sqrt3)+(3*k-1)*r*0.5))
		is_above=false;
	if (is_above)
		y-=y_shift_unit;
		
	}
//	System.out.println(" new y is: "+y);
	//check if it is inline with the initial tile
	boolean in_line_with_initial_tile =false;
	if ((k % 2)==0)
	{
		// tile boundary \/
		if (temp_x<0)
		{
			if (y< (-temp_x/sqrt3+r*(double)(3*k-2)/4)){
				in_line_with_initial_tile =true;
			}
		}
		else if (y<= (temp_x/sqrt3+r*(double)(3*k-2)/4))
			in_line_with_initial_tile =true;
			
		
	}
	else //k is odd
	{
		// tile boundary /\
		if (temp_x<0)
		{
			if (y<= (temp_x/sqrt3+r*(double)(3*k-1)/4)){
				in_line_with_initial_tile =true;
			}
		}
		else if (y<(-temp_x/sqrt3+r*(double)(3*k-1)/4))
			in_line_with_initial_tile =true;
	}
	if (!in_line_with_initial_tile){
		//slide it k* r* sqrt3/2 units down a line with slope sqrt3 
		// since delta x is k*r*sqrt3/4 => delta y= k*r*3/4
		x-= r*k*sqrt3/4.0;
		y-=r*k*3.0/4.0;
		
	}
	// now the tile is either the same as the initial tile or its either to its right or to its left
	int tentative_class_num= this.calculateClassNumWithinInitialTile(x, y, r, k, max_range);
	if (tentative_class_num !=0)
		return tentative_class_num;
	else if (x>= (r*sqrt3*(2*k-1)/8.0) )
	{
		//shift the tile to the left
		x-=x_shift_unit;
		return this.calculateClassNumWithinInitialTile(x, y, r, k, max_range);
	}else
	{
		//shift the tile to the right
		x+=x_shift_unit;
		return this.calculateClassNumWithinInitialTile(x, y, r, k, max_range);
	}
		
	
	
			
}

public int calculateClassNumInDG(double x, double y, double min_range, double max_range){
	double r = min_range/max_range;
	int k= 1+ (int)Math.ceil(4.0/(Math.sqrt(3)*r));
	
	return calculateClassNum (x+100, y+100, max_range, k, r );
}

public static void main(String args[]){
	ClassNumber cn= new ClassNumber();
	int c= cn.calculateClassNum(300, 346, 50, 6, 1);
	System.out.println(c+" ");

	
}


}

