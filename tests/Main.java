/* Commentaire avant import */
import java.io.Serializable;
// Commentaire avant la classe
public class Main { /* commentaire */
  /* this is a comment*/
  /*
  this
  is
  also a comment
   */

  public static int somme(float a, float b) /* this could be a comment too */ {
    float c = a + b;
    switch (c) {
      case c = 2:
        break;
      case c = 5:
        for (int i = 0; i < c; i++) {
          System.out.println("caca");
          if (c == 2){
            continue;
          }
        }
    }
    return (int) c; //another comment
  }
  //this is a comment for the next method
  public static int max(int[] nums) { // Commentaire
    int max = 0;
    for(int i=0; i<nums.length; i++) {
      max = Math.max(nums[i], max); /* this
      could be a comment*/
    }
    return max;
  }
  public static void main(String[] args) {
    float a = 5.5f;
    float b = 6.6f;
    System.out.println(somme(b, a));
// *** Affiche :
    System.out.println(somme(0.2f, 0.2f) + "" + somme(0.2f, 0.2f));
// *** Affiche :
    int[][] tabs = {{6,6,6},{6,6,6}};
    int[] t1 = tabs[1];
    int[] t2 = {6,6,6};
    System.out.println(tabs[0] == tabs[0]);
// *** Affiche :
    System.out.println(t1 == t2);
// *** Affiche :
    System.out.println(tabs[0] == t1);
// *** Affiche :
    System.out.println(tabs[1] == t1);
// *** Affiche :
    System.out.println(max(tabs[1]));
// *** Affiche :
    D fooTab = new D(t1);
    System.out.println(fooTab.egal(t1));
// *** Affiche :
    System.out.println(fooTab.egal(t2));
// *** Affiche :
    System.out.println(fooTab.egal(fooTab));
// *** Affiche :

  }
} // fin de classe
