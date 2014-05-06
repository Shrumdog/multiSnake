
package multiSnake;
 
public enum Direction {
       
       
       NORTH {
 
              @Override
              Point move(Point p) {return new Point(p.getRow()-1, p.getCol());}
 
              @Override
              public String getDir() {return "NORTH";}
              
       }, SOUTH {
 
              @Override
              Point move(Point p) {return new Point(p.getRow()+1, p.getCol());}
 
              @Override
              public String getDir() {return "SOUTH";}
              
       }, EAST {
 
              @Override
              Point move(Point p) {return new Point(p.getRow(), p.getCol()+1);}
 
              @Override
              public String getDir() {return "EAST";}
              
       }, WEST {
 
              @Override
              Point move(Point p) {return new Point(p.getRow(), p.getCol()-1);}
 
              @Override
              public String getDir() {return "WEST";}
              
       };
       
       abstract Point move(Point p);
       public abstract String getDir();
 
}
 