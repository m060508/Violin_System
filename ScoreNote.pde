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
