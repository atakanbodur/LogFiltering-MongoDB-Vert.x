import com.foreks.atakanbodur.starter.MainVerticle;
import io.vertx.core.Launcher;

public class Main {
  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }
}
