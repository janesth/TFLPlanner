package ch.janesthomas.tflplanner.models;

public class TubeModel {

    private String id;
    private String name;

    public TubeModel(String id, String name) {
        this.id = id;
        this.name  = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
