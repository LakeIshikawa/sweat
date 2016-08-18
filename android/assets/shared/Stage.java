package shared;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateful.Fsm;

/**
 * Fighter base class
 */
public class Stage extends Fsm<Stage, StageState, Object> {
    int camera_width = 640;
    int camera_height = 360;
    int camera_offset_y = 155;
    int fighters_height = 200;

    int area_r = 768;
    int area_l = -768;
    int area_t = 500;
    int area_b = -200;

    int p1_start_x = -130;
    int p1_start_y = 0;
    int p2_start_x = 130;
    int p2_start_y = 0;

    // References
    Fsm p1, p2;

    @Override
    public StageState getInitialState(){
        return StageState.INIT;
    }
}

/**
 * Fighter state machine
 */
enum StageState implements State<Stage> {
    INIT() {
        public void enter(Stage stage){
            Fsm p1 = stage.getFSM("p1");
            Fsm p2 = stage.getFSM("p2");
            stage.p1 = p1;
            stage.p2 = p2;

            // Set keys
            p1.setKeySettings(stage.getSettings().getP1Keys());
            p2.setKeySettings(stage.getSettings().getP2Keys());

            // Set initial positions
            p1.pos.set(stage.p1_start_x, stage.p1_start_y);
            p2.pos.set(stage.p2_start_x, stage.p2_start_y);

            // Set scale and facing direction
            p1.scale *= stage.fighters_height / p1.frameHeight(p1.getAnimation("idle"));
            p2.scale *= stage.fighters_height / p2.frameHeight(p2.getAnimation("idle"));
            p2.flip = true;

            // Add collisionts
            p1.addCollisionTarget(p2);
            p2.addCollisionTarget(p1);

            // Init camera
            stage.initCamera(0, stage.camera_offset_y, stage.camera_width, stage.camera_height);
        }

        public void update(Stage stage) {
            stage.setCamera((int)(stage.p1.pos.x + stage.p2.pos.x)/2, stage.camera_offset_y);
        }
    };

    public void update(Stage entity) {}
    public void enter(Stage entity){}
    public void exit(Stage entity){}
    public boolean onMessage(Stage entity, Telegram telegram) {return false;}
}