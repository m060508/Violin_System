class Tab{
 private int x;
 private int y;

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
  	text(""+mouseX, 40,40, 40,40);
  	text("True Position Learning", 80, 955);
    text("Ambiguous Position Learning", 270, 955);
    text("false Position Learning", 480, 955);
  }
  void mouseClicked() {
  if( ((x <= mouseX)&&(mouseX < x+200)) && ((y <= mouseY)&&(mouseY <= y+60)) )
    {
    fill(105, 105, 105);//マウスがオンされた場合，色を灰色にする
    rect(x, y, 200, 60); 
    }
}
}