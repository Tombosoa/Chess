package chess.com.board;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.NumericalReference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessBoardTest {
    @Test
    public void getCaseOnBoard(){
        var bd = new ChessBoard();

        var actualCase = bd.getCase(7, 7);
        var expectedCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.h);

        assertEquals(expectedCase, actualCase);
    }

    @Test
    public void test(){
        System.out.println(AlphabeticalReference.h.ordinal());
        System.out.println(NumericalReference.EIGHT.getValue());
    }
}
