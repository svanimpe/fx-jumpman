package world;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Level implements Serializable {

    private static final long serialVersionUID = 1;
    
    private final String name;
    private final double length;
    private final List<Launch> launches;
    
    public Level(String name, double length, List<Launch> launches) {
        if (name != null && !name.trim().equals("")) {
            this.name = name;
        } else {
            this.name = "Unnamed Level";
        }
        
        this.length = length >= 100 ? length : 100;
        
        this.launches = new ArrayList<>();
        this.launches.addAll(launches);
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }
    
    public List<Launch> getLaunchesForX(double x) {
        var toLaunch = launches.stream().filter(launch -> launch.getX() <= x).collect(Collectors.toList());
        launches.removeAll(toLaunch);
        return toLaunch;
    }
    
    public static Level fromFile(String fileName) {
        Level level = null;
        try (var in = new ObjectInputStream(Level.class.getResourceAsStream("/resources/levels/" + fileName))) {
            level = (Level)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return level;
    }
}
