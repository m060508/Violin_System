class Tab{
 private int x;
 private int y;
 private int number=0;

 Tab(int x, int y){
 	this.x = x;
 	this.y = y;
 }
public int getX() {
    return this.x;
  }
public int getY() {
    return this.y;
  }

  void tab_rect() { //何も触っていない時のTabの状態
    stroke(255);
    fill(0);
    rect(x, y, 200, 60); 
  }
  void tab_color(){ //マウスが触れたところに色をつける
    if( ((x <= mouseX)&&(mouseX < x+200)) && ((y <= mouseY)&&(mouseY <= y+60)) )
    {
    fill(105, 105, 105);//マウスが触れた場合，色を灰色にする
    rect(x, y, 200, 60); 
    }
  }
  void tab_text(){ //それぞれのタブのテキスト部分
  	fill(255);
  	text("mouseX:"+mouseX, 40,40, 90,60);
  	text("mouseY:"+mouseY, 40,60, 90,60);
  	text("True Position Learning", 80, 955);
    text("Ambiguous Position Learning", 270, 955);
    text("false Position Learning", 480, 955);

    if(number == 0){//tab_trueがマウスクリックされた時
  	fill(255);
  	text("This mode teaches only true position.", 80, 1000);
  }
  else if(number == 1){//tab_ambiguousがされた時	
  	fill(255);
  	text("This mode teaches true position and false position.", 80, 1000);
  }
  else if(number == 2){//tab_falseがマウスクリック	された時	
  	fill(255);
  	text("This mode teaches only false position.", 80, 1000);
  }
  println("Number:"+number);
  }
  void mousePressed() {
  for(int i = 0; i < 3 ;i++){
  	if((mousePressed)&&( (( 50+ 200*i <= mouseX)&&(mouseX < 50+200*i+200)) && ((920 <= mouseY)&&(mouseY <= 980)) ))
  	number = i;
  }
}

  
}