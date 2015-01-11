package app;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import world.Launch;
import world.Level;

public class GenerateLevel {
    
    public static void main(String[] args) {
        
        List<Launch> launches = new ArrayList<>();
        launches.add(new Launch(400, 50, -100));
        launches.add(new Launch(500, 25, -110));
        launches.add(new Launch(600, 0, -150));
        launches.add(new Launch(800, 25, -100));
        launches.add(new Launch(800, 0, -100));
        launches.add(new Launch(1000, 0, -100));
        launches.add(new Launch(1100, 25, -110));
        launches.add(new Launch(1200, 50, -150));
        launches.add(new Launch(1200, 0, -100));
        launches.add(new Launch(1400, 0, -100));
        
        Level level = new Level("Demo Level", 2000, launches);
        Path path = Paths.get(System.getProperty("user.dir"), "demo");
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(level);
            System.out.println("Finished writing level.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
