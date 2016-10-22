# SWEAT
SWEAT is a Scriptable Workbox, Engine And Tools for productive game development.

## Usage
- Grab the last binary release and unpack it in your project folder.
- Write an init script in Java. It should look like this:

```java
package yugen.screens;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.lksoft.sweat.stateful.Fsm;

public class MyGame extends Fsm<MyGame, MyGameState, Object> {
    public MyGameState getInitialState(){
        return MyGameState.INIT;
    }
    public void statelessUpdate() {}
}

enum MyGameState implements State<MyGame>{
    INIT(){
        public void enter(MyGame fsm){
            // Write your game here!
        }

        public void update(MyGame fsm) {}
        public void exit(MyGame fsm){}
        public boolean onMessage(MyGame entity, Telegram telegram) {return false;}
    }
}
```

- Run "settings-editor.bat", and set the init script you just created.
- Run "sweat.bat".  The system will fire up and play your init script.
