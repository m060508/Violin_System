PImage all_score, part_score, left_grad, right_grad; //全体楽譜, 楽譜の一部, 左用グラデーション, 右用グラデーション
ScoreNote[][]note = new ScoreNote[4][8];//note[y軸向きに段数][x軸向きに音数
Color []color_rect = new Color[22];//色を22色で管理
Tab tab_true, tab_ambiguous, tab_false;

void setup() {
  fullScreen(P2D); // 画面サイズを決定
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
  
  tab_true = new Tab(50, 920); //Tabの正確ver
  tab_ambiguous = new Tab(250, 920); //Tabの曖昧ver
  tab_false = new Tab(450, 920); //Tabの虚偽ver
}

void draw(){
 background(0);
 image(all_score, 800, 100, 1200, 741);//全体楽譜を配置
 //image(part_score, 90, 50, 1141, 148);//楽譜の一部
 part_score = part_score.get(0,0,680,148);
 image(part_score,90,50,680,148);
 image(left_grad, 70, 40, 88, 178);
 image(right_grad, 700, 40, 88, 178);
 tab_true.tab_rect();
 tab_true.tab_color();
 tab_true.tab_text();
 tab_true.mousePressed();
 tab_ambiguous.tab_rect();
 tab_ambiguous.tab_color();
 tab_ambiguous.tab_text();
 tab_ambiguous.mousePressed();
 tab_false.tab_rect();
 tab_false.tab_color();
 tab_false.tab_text();
 tab_false.mousePressed();
}

void mouseClicked() {
  println("x"+mouseX+" "+"y"+mouseY);
  return;
}
