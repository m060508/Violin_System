/*class NoCamera{
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
    line(x, 370, x+500, 370);//1の指用の線
    line(x, 440, x+500, 440);//2の指(G5用)用の線
    line(x, 580, x+500, 580);//2の指(C5用)用の線
    line(x, 650, x+500, 650);//3の指用の線

    fill(255);
    text("G", x+100, y-10);//G線
    text("D", x+200, y-10);//D線
    text("A", x+300, y-10);//A線
    text("E", x+400, y-10);//E線

    text("1", x+510, 370);//1の指
    text("2", x+510, 440);//2の指(G5用)
    text("2", x+510, 580);//2の指(C5用)
    text("3", x+510, 650);//3の指
	}
}*/