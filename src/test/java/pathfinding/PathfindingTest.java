package pathfinding;

import static org.junit.Assert.*;

import org.junit.Test;

import app.GamePanel;

public class PathfindingTest
{
    GamePanel gp = new GamePanel();
    Pathfinding tester = new Pathfinding(gp);

    @Test
    public void testNodeSetting()
    {
        int col = 0;
        int row = 0;

        tester.setNodes(0, 0, 15, 15);

        while (col < gp.mapM.getMap().maxWorldCol && row < gp.mapM.getMap().maxWorldRow)
        {
            assertNotNull(tester.node[col][row]);
            assertNotNull(tester.node[col][row].gCost);
            col++;

            if (col == gp.mapM.getMap().maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    @Test
    public void testGoalReached()
    {
        tester.setNodes(0, 0, 10, 10);
        tester.current.col = 0;
        tester.current.row = 0;
        tester.goal.col = 15;
        tester.goal.row = 15;
        
        assertTrue(tester.search());
    }
}
