package mp2720.prog;

import mp2720.prog.core.Logger;

class Lab3 {
    public static void main(String[] args) {
        var logger = new Logger();
        var room = new Room(logger);
        room.play();
        System.out.println(room.getLogger().toString());
    }
}
