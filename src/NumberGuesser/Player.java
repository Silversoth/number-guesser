package NumberGuesser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
    public int getCount1() {
        return count1;
    }

    /**
     * Sets the count for the first difficulty level.
     *
     * @param count1 The count to set for difficulty level 1
     */
    public void setCount1(int count1) {
        this.count1 = count1;
    }

    /**
     * Gets the count for the second difficulty level.
     *
     * @return The count for difficulty level 2
     */
    public int getCount2() {
        return count2;
    }

    /**
     * Sets the count for the second difficulty level.
     *
     * @param count2 The count to set for difficulty level 2
     */
    public void setCount2(int count2) {
        this.count2 = count2;
    }

    /**
     * Gets the count for the third difficulty level.
     *
     * @return The count for difficulty level 3
     */
    public int getCount3() {
        return count3;
    }

    /**
     * Sets the count for the third difficulty level.
     *
     * @param count3 The count to set for difficulty level 3
     */
    public void setCount3(int count3) {
        this.count3 = count3;
    }

    /**
     * Gets the points for the first difficulty level.
     *
     * @return The points for difficulty level 1
     */
    public int getPoints1() {
        return points1;
    }

    /**
     * Sets the points for the first difficulty level.
     *
     * @param points1 The points to set for difficulty level 1
     */
    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    /**
     * Gets the points for the second difficulty level.
     *
     * @return The points for difficulty level 2
     */
    public int getPoints2() {
        return points2;
    }

    /**
     * Sets the points for the second difficulty level.
     *
     * @param points2 The points to set for difficulty level 2
     */
    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    /**
     * Gets the points for the third difficulty level.
     *
     * @return The points for difficulty level 3
     */
    public int getPoints3() {
        return points3;
    }

    /**
     * Sets the points for the third difficulty level.
     *
     * @param points3 The points to set for difficulty level 3
     */
    public void setPoints3(int points3) {
        this.points3 = points3;
    }
}
