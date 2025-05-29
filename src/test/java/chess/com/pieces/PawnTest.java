package chess.com.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Pawn;
import com.chess.pieces.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnTest {
    @Test
    public void getColAndRow(){
        ChessBoard board = new ChessBoard();
        var cases = new Case(NumericalReference.TWO, AlphabeticalReference.h);

        var position = new Position(cases);

        var pawn = new Pawn(Color.white, position, board);

        var col = pawn.getPosition().getCurrentPosition().getCol();
        var row = pawn.getPosition().getCurrentPosition().getRow();

        assertEquals(7, col);
        assertEquals(2, row);
    }

    @Test
    public void getPossiblesMoves(){
        ChessBoard board = new ChessBoard();
        var cases = new Case(NumericalReference.TWO, AlphabeticalReference.h);

        var position = new Position(cases);

        var pawn = new Pawn(Color.white, position, board);
        pawn.getPosition().getCurrentPosition().setBusy(true);
        var expectedCases = new Case(NumericalReference.THREE, AlphabeticalReference.h);

        assertTrue(pawn.getPossibleMove(position.getCurrentPosition()).contains(expectedCases));

        System.out.println(pawn.getPosition().getCurrentPosition().isBusy());
    }
}
