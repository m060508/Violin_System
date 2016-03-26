PImage all_score, part_score, left_grad, right_grad; //全体楽譜, 楽譜の一部, 左用グラデーション, 右用グラデーション

void setup() {
  fullScreen(P2D); // 画面サイズを決定
}

void draw() {
 all_score = loadImage("all_score.png"); //全体楽譜を用意
 part_score = loadImage("part_score.png"); //楽譜の一部を用意
 left_grad = loadImage("left_grad.png"); //左用グラデを用意
 right_grad = loadImage("right_grad.png"); //右用グラデを用意;

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
  note[0][0]=new Note(919, 175, A4);
  note[0][1]=new Note(1044, 169, B4);
  note[0][2]=new Note(1172, 165, C5);
  note[0][3]=new Note(1299, 158, D5);
  note[0][4]=new Note(1443, 154, E5);
  note[0][5]=new Note(1577, 146, F5);
  note[0][6]=new Note(1712, 142, G5);
  note[0][7]=new Note(1846, 136, A5);

  note[1][0]=new Note(919, 175+212*1, A4);
  note[1][1]=new Note(1044, 169+212*1, B4);
  note[1][2]=new Note(1172, 165+212*1, C5);
  note[1][3]=new Note(1299, 158+212*1, D5);
  note[1][4]=new Note(1443, 154+212*1, E5);
  note[1][5]=new Note(1577, 146+212*1, F5);
  note[1][6]=new Note(1712, 142+212*1, G5);
  note[1][7]=new Note(1846, 136+212*1, A5);

  note[2][0]=new Note(919, 175+212*2, A4);
  note[2][1]=new Note(1044, 169+212*2, B4);
  note[2][2]=new Note(1172, 165+212*2, C5);
  note[2][3]=new Note(1299, 158+212*2, D5);
  note[2][4]=new Note(1443, 154+212*2, E5);
  note[2][5]=new Note(1577, 146+212*2, F5);
  note[2][6]=new Note(1712, 142+212*2, G5);
  note[2][7]=new Note(1846, 136+212*2, A5);

  note[3][0]=new Note(919, 175+212*3, A4);
  note[3][1]=new Note(1044, 169+212*3, B4);
  note[3][2]=new Note(1172, 165+212*3, C5);
  note[3][3]=new Note(1299, 158+212*3, D5);
  note[3][4]=new Note(1443, 154+212*3, E5);
  note[3][5]=new Note(1577, 146+212*3, F5);
  note[3][6]=new Note(1712, 142+212*3, G5);
  note[3][7]=new Note(1846, 136+212*3, A5);
}