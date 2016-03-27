import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Violin_System extends PApplet {

PImage all_score, part_score, left_grad, right_grad; //\u5168\u4f53\u697d\u8b5c, \u697d\u8b5c\u306e\u4e00\u90e8, \u5de6\u7528\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3, \u53f3\u7528\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3
ScoreNote[][]note = new ScoreNote[4][8];//note[y\u8ef8\u5411\u304d\u306b\u6bb5\u6570][x\u8ef8\u5411\u304d\u306b\u97f3\u6570
Color []color_rect = new Color[22];//\u8272\u309222\u8272\u3067\u7ba1\u7406
Tab tab_true, tab_ambiguous, tab_false;//\u30bf\u30d6
NoCamera camera; //\u30ab\u30e1\u30e9\u304c\u306a\u3044\u969b\u306e\u30ab\u30e1\u30e9

public void setup() {
  // \u753b\u9762\u30b5\u30a4\u30ba\u3092\u6c7a\u5b9a
 all_score = loadImage("all_score.png"); //\u5168\u4f53\u697d\u8b5c\u3092\u7528\u610f
 part_score = loadImage("part_score.png"); //\u697d\u8b5c\u306e\u4e00\u90e8\u3092\u7528\u610f
 left_grad = loadImage("left_grad.png"); //\u5de6\u7528\u30b0\u30e9\u30c7\u3092\u7528\u610f
 right_grad = loadImage("right_grad.png"); //\u53f3\u7528\u30b0\u30e9\u30c7\u3092\u7528\u610f;

//Pointer NoteName = new Pointer (NoteNumber, PointerX, PointerY);
 Pointer A4 = new Pointer(69, -200, -200);
 Pointer B4 = new Pointer(71, 528, 487);
 Pointer C5 = new Pointer(73, 531, 569);
 Pointer D5 = new Pointer(74, 536, 609);
 Pointer E5 = new Pointer(76, -200, -200);
 Pointer F5 = new Pointer(78, 551, 483);
 Pointer G5 = new Pointer(79, 551, 532);
 Pointer A5 = new Pointer(81, 556, 606);

//note[note_y][note_x] = new Note(all_score_PositionX, all_score_PositionY, NoteName);
  note[0][0] = new ScoreNote(919, 175, A4);
  note[0][1] = new ScoreNote(1044, 169, B4);
  note[0][2] = new ScoreNote(1172, 165, C5);
  note[0][3] = new ScoreNote(1299, 158, D5);
  note[0][4] = new ScoreNote(1443, 154, E5);
  note[0][5] = new ScoreNote(1577, 146, F5);
  note[0][6] = new ScoreNote(1712, 142, G5);
  note[0][7] = new ScoreNote(1846, 136, A5);

  note[1][0] = new ScoreNote(919, 175+212*1, A4);
  note[1][1] = new ScoreNote(1044, 169+212*1, B4);
  note[1][2] = new ScoreNote(1172, 165+212*1, C5);
  note[1][3] = new ScoreNote(1299, 158+212*1, D5);
  note[1][4] = new ScoreNote(1443, 154+212*1, E5);
  note[1][5] = new ScoreNote(1577, 146+212*1, F5);
  note[1][6] = new ScoreNote(1712, 142+212*1, G5);
  note[1][7] = new ScoreNote(1846, 136+212*1, A5);

  note[2][0] = new ScoreNote(919, 175+212*2, A4);
  note[2][1] = new ScoreNote(1044, 169+212*2, B4);
  note[2][2] = new ScoreNote(1172, 165+212*2, C5);
  note[2][3] = new ScoreNote(1299, 158+212*2, D5);
  note[2][4] = new ScoreNote(1443, 154+212*2, E5);
  note[2][5] = new ScoreNote(1577, 146+212*2, F5);
  note[2][6] = new ScoreNote(1712, 142+212*2, G5);
  note[2][7] = new ScoreNote(1846, 136+212*2, A5);

  note[3][0] = new ScoreNote(919, 175+212*3, A4);
  note[3][1] = new ScoreNote(1044, 169+212*3, B4);
  note[3][2] = new ScoreNote(1172, 165+212*3, C5);
  note[3][3] = new ScoreNote(1299, 158+212*3, D5);
  note[3][4] = new ScoreNote(1443, 154+212*3, E5);
  note[3][5] = new ScoreNote(1577, 146+212*3, F5);
  note[3][6] = new ScoreNote(1712, 142+212*3, G5);
  note[3][7] = new ScoreNote(1846, 136+212*3, A5);

//color_rect[number] = new Color(R, G, B)
  color_rect[0] = new Color(0, 0, 255);
  color_rect[1] = new Color(38, 92, 170);
  color_rect[2] = new Color(65, 131, 197);
  color_rect[3] = new Color(112, 160, 214);
  color_rect[4] = new Color(38, 187, 238);
  color_rect[5] = new Color(131, 206, 237);
  color_rect[6] = new Color(160, 213, 205);
  color_rect[7] = new Color(82, 186, 155);
  color_rect[8] = new Color(9, 127, 93);
  color_rect[9] = new Color(29, 117, 57);
  color_rect[10] = new Color(36, 155, 58);

  color_rect[11] = new Color(87, 175, 79);
  color_rect[12] = new Color(111, 189, 105);
  color_rect[13] = new Color(211, 227, 142);
  color_rect[14] = new Color(248, 229, 141);
  color_rect[15] = new Color(245, 211, 60);
  color_rect[16] = new Color(244, 161, 55);
  color_rect[17] = new Color(243, 162, 134);
  color_rect[18] = new Color(246, 189, 187);
  color_rect[19] = new Color(238, 129, 127);
  color_rect[20] = new Color(234, 93, 87);
  color_rect[21] = new Color(255, 0, 0);
  
  tab_true = new Tab(50, 920); //Tab\u306e\u6b63\u78baver
  tab_ambiguous = new Tab(250, 920); //Tab\u306e\u66d6\u6627ver
  tab_false = new Tab(450, 920); //Tab\u306e\u865a\u507dver

  camera = new NoCamera(170, 300);
}

public void draw(){
 background(0);

//\u697d\u8b5c\u306e\u8868\u793a
 image(all_score, 800, 100, 1200, 741);//\u5168\u4f53\u697d\u8b5c\u3092\u914d\u7f6e
 //image(part_score, 90, 50, 4559, 148);//\u697d\u8b5c\u306e\u4e00\u6bb5\u843d\u3092\u914d\u7f6e
 part_score = part_score.get(0,0,680,148);//\u697d\u8b5c\u306e\u4e00\u6bb5\u843d\u306e\u3046\u3061\u5f3e\u3044\u3066\u3044\u308b\u7b87\u6240\u306e\u307f\u5207\u308a\u629c\u304d
 image(part_score,90,50,680,148);//\u5207\u308a\u629c\u3044\u305f\u697d\u8b5c\u3092\u8868\u793a
 image(left_grad, 70, 40, 88, 178); //\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3\u5de6\u3092\u914d\u7f6e
 image(right_grad, 700, 40, 88, 178);//\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3\u53f3\u3092\u914d\u7f6e

 //Tab\u306e\u52d5\u304d\u3092\u7ba1\u7406
 tab_true.tab_color();//\u6b63\u78ba\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u8272\u306e\u72b6\u614b
 tab_true.tab_text();//\u6b63\u78ba\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u6587\u7ae0\u3092\u7ba1\u7406
 tab_true.mousePressed();//\u6b63\u78ba\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059\u30de\u30a6\u30b9\u30af\u30ea\u30c3\u30af\u3055\u308c\u305f\u6642\u306e\u7bc4\u56f2\u3092\u7ba1\u7406

 tab_ambiguous.tab_color();//\u66d6\u6627\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u8272\u306e\u72b6\u614b
 tab_ambiguous.tab_text();//\u66d6\u6627\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u6587\u7ae0\u3092\u7ba1\u7406
 tab_ambiguous.mousePressed();//\u66d6\u6627\u306a\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059\u30de\u30a6\u30b9\u30af\u30ea\u30c3\u30af\u3055\u308c\u305f\u6642\u306e\u7bc4\u56f2\u3092\u7ba1\u7406

 tab_false.tab_color();//\u865a\u507d\u306e\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u8272\u306e\u72b6\u614b
 tab_false.tab_text();//\u865a\u507d\u306e\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059Tab\u306e\u6587\u7ae0\u3092\u7ba1\u7406
 tab_false.mousePressed();//\u865a\u507d\u306e\u30dd\u30b8\u30b7\u30e7\u30cb\u30f3\u30b0\u3092\u793a\u3059\u30de\u30a6\u30b9\u30af\u30ea\u30c3\u30af\u3055\u308c\u305f\u6642\u306e\u7bc4\u56f2\u3092\u7ba1\u7406

 camera.camera_drawing();
}

public void mouseClicked() {
  println("x"+mouseX+" "+"y"+mouseY);
  return;
}
class Color{ //\u97f3\u306e\u5909\u5316\u306e\u8272\u3092\u793a\u3059\u30af\u30e9\u30b9
  private int r;
  private int g;
  private int b;
  Color(int r, int g, int b){ 
 
    this.r=r;
    this.g=g;
    this.b=b;
  }
  public int getR() {
    return this.r;
  }
  public int getG() {
    return this.g;
  }
  public int getB() {
    return this.b;
  }

  public void color_rect() {
    noStroke();
    fill(r, g, b);
  }
}
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

public void camera_drawing(){
	fill(105,105,105); //\u30ab\u30e1\u30e9\u304c\u306a\u3044\u7528\u306e\u30dc\u30c3\u30af\u30b9
	rect(x, y, 500, 470);
    for(int i =0; i < 4; i++){
    	line(x+100+100*i, y, x+100+100*i, y+470);//\u7e26\u7dda\u3092\u7528\u610f(G\u301cE\u7dda)	
    }
    stroke(0);
    line(x, 370, x+500, 370);//1\u306e\u6307
    line(x, 440, x+500, 440);//2\u306e\u6307(G5\u7528)
    
    line(x, 580, x+500, 580);//2\u306e\u6307(C5\u7528)
    line(x, 650, x+500, 650);//3\u306e\u6307
	}

}
class Pointer{
  private int midi_value;
  private int pos_X;
  private int pos_Y;
  
  Pointer(int midi_value, int pos_X, int pos_Y){
    this.midi_value = midi_value;
    this.pos_X = pos_X;
    this.pos_Y = pos_Y;
  }
}
class ScoreNote {
  private int x;
  private int y;
  private Pointer pointer;
 
 ScoreNote(int x, int y, Pointer pointer){
 	this.x = x;
 	this.y = y;
 	this.pointer = pointer;
 }
 public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }
  public Pointer pointer() {
    return this.pointer;
  }
 }
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

  public void tab_color(){ //\u30de\u30a6\u30b9\u304c\u89e6\u308c\u305f\u3068\u3053\u308d\u306b\u8272\u3092\u3064\u3051\u308b
    stroke(255);
    fill(0);
    rect(x, y, 200, 60); 
    if( ((x <= mouseX)&&(mouseX < x+200)) && ((y <= mouseY)&&(mouseY <= y+60)) )
    {
    fill(105, 105, 105);//\u30de\u30a6\u30b9\u304c\u89e6\u308c\u305f\u5834\u5408\uff0c\u8272\u3092\u7070\u8272\u306b\u3059\u308b
    rect(x, y, 200, 60); 
    }
  }
  public void tab_text(){ //\u305d\u308c\u305e\u308c\u306e\u30bf\u30d6\u306e\u30c6\u30ad\u30b9\u30c8\u90e8\u5206
  	fill(255);
  	text("mouseX:"+mouseX, 40,40, 90,60);
  	text("mouseY:"+mouseY, 40,60, 90,60);
  	text("True Position Learning", 80, 955);
    text("Ambiguous Position Learning", 270, 955);
    text("false Position Learning", 480, 955);

    if(number == 0){//tab_true\u304c\u30de\u30a6\u30b9\u30af\u30ea\u30c3\u30af\u3055\u308c\u305f\u6642
  	fill(255);
  	text("This mode teaches only true position.", 80, 1000);
  }
  else if(number == 1){//tab_ambiguous\u304c\u3055\u308c\u305f\u6642	
  	fill(255);
  	text("This mode teaches true position and false position.", 80, 1000);
  }
  else if(number == 2){//tab_false\u304c\u30de\u30a6\u30b9\u30af\u30ea\u30c3\u30af	\u3055\u308c\u305f\u6642	
  	fill(255);
  	text("This mode teaches only false position.", 80, 1000);
  }
  //println("Number:"+number);
  }

  public void mousePressed() {
  for(int i = 0; i < 3 ;i++){
  	if((mousePressed)&&( (( 50+ 200*i <= mouseX)&&(mouseX < 50+200*i+200)) && ((920 <= mouseY)&&(mouseY <= 980)) ))
  	number = i;
  }
}

  
}
  public void settings() {  fullScreen(P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Violin_System" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
