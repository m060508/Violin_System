class NoCamera{
	private int x;
	private int y;

NoCamera(int x, int y){
		this.x = x;
		this.y = y;
	}

public int getX(){
      return this.x;
	}
	public int getY(){
     return this.y;
	}

void camera_drawing(){
	fill(105,105,105); //カメラがない用のボックス
	rect(x, y, 500, 470);
    for(int i =0; i < 4; i++){
    	line(x+100+100*i, y, x+100+100*i, y+470);//縦線を用意(G〜E線)	
    }
    stroke(0);
    line(x, 370, x+500, 370);//1の指
    line(x, 440, x+500, 440);//2の指(G5用)
    
    line(x, 580, x+500, 580);//2の指(C5用)
    line(x, 650, x+500, 650);//3の指
	}

}