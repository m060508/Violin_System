import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import themidibus.*; 
import javax.sound.midi.MidiMessage; 
import javax.sound.midi.SysexMessage; 
import javax.sound.midi.ShortMessage; 
import processing.video.*; 
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

 //Import the library
 //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html


  //\u30d3\u30c7\u30aa\u30e9\u30a4\u30d6\u30e9\u30ea\u3092\u30a4\u30f3\u30dd\u30fc\u30c8


//\u753b\u50cf\u7528\u5909\u6570
PImage all_score, part_score, left_grad, right_grad; //\u5168\u4f53\u697d\u8b5c, \u697d\u8b5c\u306e\u4e00\u90e8, \u5de6\u7528\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3, \u53f3\u7528\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3

//\u4e3b\u306b\u697d\u8b5c\u306e\u97f3\u3092\u7ba1\u7406\u3059\u308b\u7528
ScoreNote[][]note = new ScoreNote[4][8];//note[y\u8ef8\u5411\u304d\u306b\u6bb5\u6570][x\u8ef8\u5411\u304d\u306b\u97f3\u6570
int note_y, note_x = 0;

//\u8272\u3092\u7ba1\u7406\u3059\u308b\u305f\u3081\u306e\u914d\u5217
Color []col = new Color[22];//\u8272\u309222\u8272\u3067\u7ba1\u7406

//\u30bf\u30d6\u3092\u7ba1\u7406\u3059\u308b\u305f\u3081\u306e\u5909\u6570
Tab tab_true, tab_ambiguous, tab_false;//\u30bf\u30d6
//NoCamera camera; //\u30ab\u30e1\u30e9\u304c\u306a\u3044\u969b\u306e\u30ab\u30e1\u30e9

//web\u30ab\u30e1\u30e9\u7528
Capture video;  //Capture\u578b\u306e\u5909\u6570video\u3092\u5ba3\u8a00

//midi\u7528
MidiBus myBus; //The MidiBus
int pitchbend, notebus_different=0;//note_y\u306f\u6bb5\u843d\u6570\u3001note_x\u3067\u6bb5\u843d\u5185\u306e\u4f55\u756a\u76ee\u3092\u5f3e\u3044\u3066\u3044\u308b\u304b\u7ba1\u7406

int channel = 0;
int pitch = 64;
int velocity = 127;
int status_byte = 0xA0; // For instance let us send aftertouch
int channel_byte = 0; // On channel 0 again
int first_byte = 64; // The same note;
int second_byte = 80; // But with less velocity

ArrayList<ScoreNote> played_note;//pitchbend\u3067\u5f97\u305f\u3069\u306e\u7a0b\u5ea6\u305a\u308c\u3066\u3044\u308b\u304b\u3092\u5165\u308c\u308b\u305f\u3081\u306e\u914d\u5217\u3092\u7528\u610f

public void setup() {
  //\u753b\u9762
  // \u753b\u9762\u30b5\u30a4\u30ba\u3092\u6c7a\u5b9a

//midibus\u7528
  MidiBus.list(); // List all available Midi devices on STDOUT. This will show each device's index and name.
  myBus = new MidiBus(this, 0, 0); // Create a new MidiBus object

 //\u30ab\u30e1\u30e9\u306e\u6e96\u5099
  video = new Capture(this, 640, 540,"USB_Camera");  //\u30ab\u30e1\u30e9\u304b\u3089\u306e\u30ad\u30e3\u30d7\u30c1\u30e3\u30fc\u3092\u304a\u3053\u306a\u3046\u305f\u3081\u306e\u5909\u6570\u3092\u8a2d\u5b9a
  video.start();  //Processing ver.2.0\u4ee5\u4e0a\u306f\u3053\u306e\u30b3\u30fc\u30c9\u304c\u5fc5\u8981

//\u5404\u753b\u50cf\u3092\u7528\u610f
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

//col[number] = new Color(R, G, B)
  col[0] = new Color(0, 0, 255);
  col[1] = new Color(38, 92, 170);
  col[2] = new Color(65, 131, 197);
  col[3] = new Color(112, 160, 214);
  col[4] = new Color(38, 187, 238);
  col[5] = new Color(131, 206, 237);
  col[6] = new Color(160, 213, 205);
  col[7] = new Color(82, 186, 155);
  col[8] = new Color(9, 127, 93);
  col[9] = new Color(29, 117, 57);
  col[10] = new Color(36, 155, 58);

  col[11] = new Color(87, 175, 79);
  col[12] = new Color(111, 189, 105);
  col[13] = new Color(211, 227, 142);
  col[14] = new Color(248, 229, 141);
  col[15] = new Color(245, 211, 60);
  col[16] = new Color(244, 161, 55);
  col[17] = new Color(243, 162, 134);
  col[18] = new Color(246, 189, 187);
  col[19] = new Color(238, 129, 127);
  col[20] = new Color(234, 93, 87);
  col[21] = new Color(255, 0, 0);
  
  tab_true = new Tab(50, 920);//Tab\u306e\u6b63\u78baver
  tab_ambiguous = new Tab(250, 920);//Tab\u306e\u66d6\u6627ver
  tab_false = new Tab(450, 920);//Tab\u306e\u865a\u507dver

 // camera = new NoCamera(170, 300);
 
 //midibus\u3092\u7ba1\u7406
 myBus.sendNoteOn(channel, pitch, velocity); // Send a Midi noteOn
  myBus.sendNoteOff(channel, pitch, velocity); // Send a Midi nodeOff
  myBus.sendMessage(status_byte, channel_byte, first_byte, second_byte);
  myBus.sendMessage(
    new byte[] {
    (byte)0xF0, (byte)0x1, (byte)0x2, (byte)0x3, (byte)0x4, (byte)0xF7
    }
    );
  try { 
    SysexMessage message = new SysexMessage();
    message.setMessage(
      0xF0, 
      new byte[] {
      (byte)0x5, (byte)0x6, (byte)0x7, (byte)0x8, (byte)0xF7
      }, 
      5
      );
    myBus.sendMessage(message);
  } 
  catch(Exception e) {
  }

}

public void draw(){
 background(0);

 //\u30ab\u30e1\u30e9\u306e\u8abf\u6574\u3068\u8868\u793a
video.read();

//\u30ab\u30e1\u30e9\u6620\u50cf\u3092\u56de\u8ee2\u3055\u305b\u3066\u3001\u6f14\u594f\u8005\u306e\u898b\u3066\u3044\u308b\u3082\u306e\u3068\u540c\u3058\u6620\u50cf\u306b\u3059\u308b
  pushMatrix(); 
  translate(100, 900);
  rotate(radians(-90));
  image(video, 10, 10, 640, 540);
  popMatrix();

//\u697d\u8b5c\u306e\u8868\u793a
 image(all_score, 800, 100, 1200, 741);//\u5168\u4f53\u697d\u8b5c\u3092\u914d\u7f6e
 //image(part_score, 90, 50, 4559, 148);//\u697d\u8b5c\u306e\u4e00\u6bb5\u843d\u3092\u914d\u7f6e
 part_score = part_score.get(0,0,680,148);//\u697d\u8b5c\u306e\u4e00\u6bb5\u843d\u306e\u3046\u3061\u5f3e\u3044\u3066\u3044\u308b\u7b87\u6240\u306e\u307f\u5207\u308a\u629c\u304d
 image(part_score,90,50,680,148);//\u5207\u308a\u629c\u3044\u305f\u697d\u8b5c\u3092\u8868\u793a
 image(left_grad, 70, 40, 88, 178); //\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3\u5de6\u3092\u914d\u7f6e
 image(right_grad, 700, 40, 88, 178);//\u30b0\u30e9\u30c7\u30fc\u30b7\u30e7\u30f3\u53f3\u3092\u914d\u7f6e

//\u697d\u8b5c\u306e\u6c34\u8272\u25bc\u3092\u8868\u793a
note[note_y][note_x].blue_triangle(); 

//\u305a\u308c\u5225\u306e\u8272\u306e\u898b\u672c\u3092\u8868\u793a
  for (int i = 0; i < col.length; i++) {
    col[i].color_rect();
    rect(1000+i*30, 20, 20, 20);
  }
  fill(255);
  textSize(20);
  text("low tone", 900, 20, 100, 40);
  text("high tone", 1670, 20, 100, 40);

//\u305d\u306e\u5834\u3067\u5f3e\u3044\u305f\u97f3\u306e\u305a\u308c\u3092\u8868\u793a
if (note[note_y][note_x].played_note.size()>0) {
    col[note[note_y][note_x].getNote(note[note_y][note_x].played_note.size()-1)].color_rect();
    rect(200, 160, 30, 30);
  }


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
}

//midibus\u3092\u7ba1\u7406\u3057\u3066\u3044\u308b
public void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data, String bus_name) 
  println();
  print("Status Byte/MIDI Command:"+(int)(data[0] & 0xFF));
  if (((int)(data[0] & 0xFF) >= 224)&&((int)(data[0] & 0xFF) <= 227)) {
    pitchbend = (int)(data[2] & 0xFF) * 128 + (int)(data[1] & 0xFF);
  } 
 for (int i = 1; i < data.length; i++) {
    print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
 }
 for (int i = 1; i < data.length; i++) {
 print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
  }
if (((int)(data[0] & 0xFF) >= 144)&&((int)(data[0] & 0xFF) <= 171)) {
    notebus_different=((data[1] & 0xFF)-(note[note_y][note_x].pointer()).MidiValue())*333+pitchbend-8192;
    note[note_y][note_x].addNote(notebus_different);
  }
if (((int)(data[0] & 0xFF) >= 128)&&((int)(data[0] & 0xFF) <= 131)) {
    println();
 if ((int)(data[1] & 0xFF)!=(note[note_y][note_x].pointer()).MidiValue()) {      
    }
    if ((int)(data[1] & 0xFF)==(note[note_y][note_x].pointer()).MidiValue()) {
      note_x++;
      if (note_x!=0&&note_x==8) {
        note_y++;
        note_x=0;
        if (note_y>3) {
          note_y=0;
        }
      }
    }
  }
}

//web\u30ab\u30e1\u30e9\u3092\u66f4\u65b0
public void captureEvent(Capture video) {
  video.read();
}

//\u30de\u30a6\u30b9\u306e\u4f4d\u7f6e\u3092\u628a\u63e1
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
	fill(105,105,105); //\u30ab\u30e1\u30e9\u304c\u306a\u3044\u7528\u306e\u30dc\u30c3\u30af\u30b9
	rect(x, y, 500, 470);
    for(int i =0; i < 4; i++){
    	line(x+100+100*i, y, x+100+100*i, y+470);//\u7e26\u7dda\u3092\u7528\u610f(G\u301cE\u7dda)	
    }
    stroke(0);
    line(x, 370, x+500, 370);//1\u306e\u6307\u7528\u306e\u7dda
    line(x, 440, x+500, 440);//2\u306e\u6307(G5\u7528)\u7528\u306e\u7dda
    line(x, 580, x+500, 580);//2\u306e\u6307(C5\u7528)\u7528\u306e\u7dda
    line(x, 650, x+500, 650);//3\u306e\u6307\u7528\u306e\u7dda

    fill(255);
    text("G", x+100, y-10);//G\u7dda
    text("D", x+200, y-10);//D\u7dda
    text("A", x+300, y-10);//A\u7dda
    text("E", x+400, y-10);//E\u7dda

    text("1", x+510, 370);//1\u306e\u6307
    text("2", x+510, 440);//2\u306e\u6307(G5\u7528)
    text("2", x+510, 580);//2\u306e\u6307(C5\u7528)
    text("3", x+510, 650);//3\u306e\u6307
	}
}*/
class Pointer{
  private int midi_value;
  private int pos_x;
  private int pos_y;
  
  Pointer(int midi_value, int pos_x, int pos_y){
    this.midi_value = midi_value;
    this.pos_x = pos_x;
    this.pos_y = pos_y;
  }

  public int MidiValue(){
  	return this.midi_value;
  }
  public int PosX(){
  	return this.pos_x;
  }
  public int PosY(){
  	return this.pos_y;
  }
}
class ScoreNote {
  private int x;
  private int y;
  private Pointer pointer;
  private ArrayList<Integer> played_note = new ArrayList();
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

public void addNote(int n)
  {
    if (n<-300) {
      n=0;
    } else if ((-300<=n)&&(n<-270)) {
      n=1;
    } else if ((-270<=n)&&(n<-240)) {
      n=2;
    } else if ((-240<=n)&&(n<-210)) {
      n=3;
    } else if ((-210<=n)&&(n<-180)) {
      n=4;
    } else if ((-180<=n)&&(n<-150)) {
      n=5;
    } else if ((-150<=n)&&(n<-120)) {
      n=6;
    } else if ((-120<=n)&&(n<-90)) {
      n=7;
    } else if ((-90<=n)&&(n<-60)) {
      n=8;
    } else if ((-60<=n)&&(n<-30)) {
      n=9;
    } else if ((-30<=n)&&(n<0)) {
      n=10;
    } else if ((0<=n)&&(n<30)) {
      n=11;
    } else if ((30<=n)&&(n<60)) {
      n=12;
    } else if ((60<=n)&&(n<90)) {
      n=13;
    } else if ((90<=n)&&(n<120)) {
      n=14;
    } else if ((120<=n)&&(n<150)) {
      n=15;
    } else if ((150<=n)&&(n<180)) {
      n=16;
    } else if ((180<=n)&&(n<210)) {
      n=17;
    } else if ((210<=n)&&(n<240)) {
      n=18;
    } else if ((240<=n)&&(n<270)) {
      n=19;
    } else if ((270<=n)&&(n<300)) {
      n=20;
    } else if (300<=n) {
      n=21;
    }
    played_note.add(n);
  }
  public int getNote(int m) {
    return this.played_note.get(m);
  }
  public void blue_triangle() {//\u6c34\u8272\u25bc\u306e\u4f4d\u7f6e\u3068\u5f62\u3092\u7ba1\u7406  
    noStroke();
    fill(186, 233, 255);
    textSize(25);
    text("\u25bc", x-20, 67+212*note_y, 40, 40);
    text("\u25bc", 210, 38, 40, 40);
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
    textSize(12);
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
