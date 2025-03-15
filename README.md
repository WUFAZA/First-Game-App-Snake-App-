# First-Game-App-Snake-App-

Welcome to Snake App, a simple yet engaging implementation of the classic Snake game. This project was created as a way to test and enhance my skills in object-oriented programming (OOP).

## Features
Object-oriented structure to ensure clean, modular, and reusable code.

Core functionalities for player control, snake movement, and food collection.

Scalable design for future enhancements and new features.

## Purpose
The primary goal of this project was to apply and solidify my understanding of OOP principles such as:

-`Encapsulation`

-`Inheritance`

-`Polymorphism`

-`Abstraction`

## Technologies Used
Language: [Specify programming language used, e.g., Python, Java, C++]

Libraries/Frameworks: Android SDK

## Code Snippet
### Snake Class - Movement and Growth
``` java
public void move() {
    for (int i = segments.size() - 1; i > 0; i--) {
        segments.get(i).x = segments.get(i - 1).x;
        segments.get(i).y = segments.get(i - 1).y;
    }
    Segment head = segments.get(0);
    switch (direction) {
        case Direction.UP: head.y -= UNIT_SIZE; break;
        case Direction.DOWN: head.y += UNIT_SIZE; break;
        case Direction.LEFT: head.x -= UNIT_SIZE; break;
        case Direction.RIGHT: head.x += UNIT_SIZE; break;
    }
}
```
### Food Class - Spawn Functionality
```java
public void spawn() {
    x = (random.nextInt(screenWidth / UNIT_SIZE)) * UNIT_SIZE;
    y = (random.nextInt(screenHeight / UNIT_SIZE)) * UNIT_SIZE;
}
```
### GameView Class - Core Game Loop
```java
@Override
public void run() {
    while (isPlaying) {
        long startTime = System.currentTimeMillis();
        update();
        draw();
        long duration = System.currentTimeMillis() - startTime;
        long sleepTime = (1000 / FRAME_RATE) - duration;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

```
### MainActivity Class - Setting Up the Game View
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    GameView gameView = new GameView(this);
    FrameLayout gameFrame = findViewById(R.id.gameFrame);
    gameFrame.addView(gameView);
}

```
## Future Enhancements
Add levels with increasing difficulty.

Implement different snake skins and themes.

Incorporate a leaderboard to track high scores.

Enable multiplayer support.

## Contributing
Contributions are welcome! If you have ideas or improvements, feel free to open an issue or submit a pull request.

## License
[Specify the license under which your code is shared.]
