package numberguesser.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Represents a player in the Number Guesser game.
 * Implements Externalizable for custom serialization.
 */
public class Player implements Externalizable {
    private String name;
    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private int points1 = 0;
    private int points2 = 0;
    private int points3 = 0;

    //CONSTRUCTORS

    /**
     * Default constructor initializing a guest player with default values.
     */
    public Player() {
        this.name = "guest";
        this.count1 = 0;
        this.count2 = 0;
        this.count3 = 0;
        this.points1 = 0;
        this.points2 = 0;
        this.points3 = 0;
    }

    /**
     * Constructs a Player with a specified name.
     *
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
    }

    //SERIALIZATION

    /**
     * Serializes the player object to the specified ObjectOutput.
     *
     * @param out The ObjectOutput to write the player data to
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name);
        out.writeObject(this.count1);
        out.writeObject(this.count2);
        out.writeObject(this.count3);
        out.writeObject(this.points1);
        out.writeObject(this.points2);
        out.writeObject(this.points3);
    }

    /**
     * Deserializes the player object from the specified ObjectInput.
     *
     * @param in The ObjectInput to read the player data from
     * @throws IOException            If an I/O error occurs
     * @throws ClassNotFoundException If the class of a serialized object cannot be found
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        count1 = (Integer) in.readObject();
        count2 = (Integer) in.readObject();
        count3 = (Integer) in.readObject();
        points1 = (Integer) in.readObject();
        points2 = (Integer) in.readObject();
        points3 = (Integer) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }



    //GETTERS AND SETTERS

    /**
     * Gets the name of the player.
     *
     * @return The player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the count for the first difficulty level.
     *
     * @return The count for difficulty level 1
     */
    public int getEasyCount() {
        return count1;
    }

    /**
     * Sets the count for the first difficulty level.
     *
     * @param count1 The count to set for difficulty level 1
     */
    public void setEasyCount(int count1) {
        this.count1 = count1;
    }

    /**
     * Gets the count for the second difficulty level.
     *
     * @return The count for difficulty level 2
     */
    public int getMediumCount() {
        return count2;
    }

    /**
     * Sets the count for the second difficulty level.
     *
     * @param count2 The count to set for difficulty level 2
     */
    public void setMediumCount(int count2) {
        this.count2 = count2;
    }

    /**
     * Gets the count for the third difficulty level.
     *
     * @return The count for difficulty level 3
     */
    public int getHardCount() {
        return count3;
    }

    /**
     * Sets the count for the third difficulty level.
     *
     * @param count3 The count to set for difficulty level 3
     */
    public void setHardCount(int count3) {
        this.count3 = count3;
    }

    /**
     * Gets the points for the first difficulty level.
     *
     * @return The points for difficulty level 1
     */
    public int getEasyPoints() {
        return points1;
    }

    /**
     * Sets the points for the first difficulty level.
     *
     * @param points1 The points to set for difficulty level 1
     */
    public void setEasyPoints(int points1) {
        this.points1 = points1;
    }

    /**
     * Gets the points for the second difficulty level.
     *
     * @return The points for difficulty level 2
     */
    public int getMediumPoints() {
        return points2;
    }

    /**
     * Sets the points for the second difficulty level.
     *
     * @param points2 The points to set for difficulty level 2
     */
    public void setMediumPoints(int points2) {
        this.points2 = points2;
    }

    /**
     * Gets the points for the third difficulty level.
     *
     * @return The points for difficulty level 3
     */
    public int getHardPoints() {
        return points3;
    }

    /**
     * Sets the points for the third difficulty level.
     *
     * @param points3 The points to set for difficulty level 3
     */
    public void setHardPoints(int points3) {
        this.points3 = points3;
    }
}
