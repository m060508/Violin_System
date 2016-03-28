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

  void point(){//抑えるべき位置を赤線で表示
    stroke(255, 0, 0);
    line(110,(note[note_y][note_x].pointer()).pos_y, 650, (note[note_y][note_x].pointer()).pos_y);
  }
  void string_point(){//抑えるべき弦を青線で表示
    if(((note[note_y][note_x].pointer()).midi_value >= 69) && ((note[note_y][note_x].pointer()).midi_value < 75)){
     stroke(0, 0, 255);
      line(431,365,452, 893);
    }
    if(((note[note_y][note_x].pointer()).midi_value >= 75) && ((note[note_y][note_x].pointer()).midi_value < 82)){
  stroke(0, 0, 255);
      line(448,365,470, 893);
  }
  }
}