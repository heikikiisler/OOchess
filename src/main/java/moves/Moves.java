package moves;

import board.Board;
import board.Square;
import board.Squares;

import java.util.*;
import java.util.function.Predicate;


public class Moves {

    public Board board;
    private int side;
    private List<Move> normalMoves;
    private List<Move> attackMoves;
    private List<DefendMove> defendMoves;
    private List<Move> specialMoves;
    private Set<Square> attackedSquares;

    public Moves(Board board) {
        this.board = board;
        this.side = this.board.getSideToMove();
        this.normalMoves = new ArrayList<>();
        this.attackMoves = new ArrayList<>();
        this.defendMoves = new ArrayList<>();
        this.specialMoves = new ArrayList<>();
        this.attackedSquares = new HashSet<>();
        getAllAvailableMoves();
    }

    private void getAvailableMoves(Square square) {
        char piece = Character.toLowerCase(board.getPiece(square));
        switch (piece) {
            case 'p':
                getPawnMoves(square);
                break;
            case 'n':
                getKnightMoves(square);
                break;
            case 'b':
                getBishopMoves(square);
                break;
            case 'r':
                getRookMoves(square);
                break;
            case 'q':
                getQueenMoves(square);
                break;
            case 'k':
                getKingMoves(square);
                break;
            case '.':
                break;
            default: throw new IllegalArgumentException();
        }
    }

    private void getAllAvailableMoves() {
        Square[] squares = Squares.getAll();
        for (Square square: squares) {
            char piece = board.getPiece(square);
            if (Board.pieceIsSide(piece, side)) {
                getAvailableMoves(square);
            }
        }
        getEnPassantMoves();
        getCastlingMoves();
    }

    private void getKnightMoves(Square square) {
        int[][][] vectors = {{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}}};
        getVectorMoves(square, vectors);
    }

    private void getKingMoves(Square square) {
        int[][][] vectors = {{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}};
        getVectorMoves(square, vectors);
    }

    private void getRookMoves(Square square) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}}};
        getVectorMoves(square, vectors);
    }

    private void getBishopMoves(Square square) {
        int[][][] vectors =
            {{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(square, vectors);
    }

    private void getQueenMoves(Square square) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}},
            {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(square, vectors);
    }

    private void getVectorMoves(Square square, int[][][] vectors) {
        for (int[][] subVectors: vectors) {
            for (int[] vector: subVectors) {
                if (square.isOffsetOnBoard(vector[0], vector[1])) {
                    Square endSquare = square.getOffsetSquare(vector[0], vector[1]);
                    char targetPiece = board.getPiece(endSquare);
                    if (targetPiece == '.') {
                        addNormalMove(square, endSquare);
                    } else if (Board.getPieceSide(targetPiece) != side) {
                        addAttackMove(square, endSquare, targetPiece);
                        break;
                    } else {
                        addDefendMove(square, endSquare, targetPiece);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    private void getPawnMoves(Square square) {
        boolean isPromoting = side == 1 && square.getRow() == 6 || side == -1 && square.getRow() == 1;
        Square forwardSquare = square.getOffsetSquare(side, 0);
        int[] takingColOffsets = new int[]{1, -1};
        if (board.isSquareEmpty(forwardSquare)) {
            if (isPromoting) {
                board.printBoard();
                getPromotionMovesForSquares(square, forwardSquare);
            } else {
                addNormalMove(square, forwardSquare);
                if ((side == 1 && square.getRow() == 1) || (side == -1 && square.getRow() == 6)) {
                    Square doubleForwardSquare = square.getOffsetSquare(side * 2, 0);
                    if (board.isSquareEmpty(doubleForwardSquare)) {
                        specialMoves.add(new PawnDoubleMove(square, doubleForwardSquare));
                    }
                }
            }
        }
        for (int takingColOffset: takingColOffsets) {
            if (square.isOffsetOnBoard(side, takingColOffset)) {
                Square takingSquare = square.getOffsetSquare(side, takingColOffset);
                if (!board.isSquareEmpty(takingSquare)) {
                    if (board.getPieceSide(takingSquare) == -side) {
                        if (isPromoting) {
                            getPromotionMovesForSquares(square, takingSquare);
                        } else {
                            addAttackMove(square, takingSquare, board.getPiece(takingSquare));
                        }
                    } else {
                        addDefendMove(square, takingSquare, board.getPiece(takingSquare));
                    }
                }
            }
        }
    }

    private void getPromotionMovesForSquares(Square startSquare, Square endSquare) {
        char[] promotionPieces = (side == 1) ? Board.getWhitePromotionPieces() : Board.getBlackPromotionPieces();
        for (char piece: promotionPieces) {
            specialMoves.add(new PromotionMove(startSquare, endSquare, piece));
        }
    }

    private void getCastlingMoves() {
        if (side == 1) {
            getCastlingMove(false, Squares.get(0, 1), Squares.get(0, 2), Squares.get(0, 3));
            getCastlingMove(true, Squares.get(0, 5), Squares.get(0, 6));
        } else {
            getCastlingMove(false, Squares.get(7, 1), Squares.get(7, 2), Squares.get(7, 3));
            getCastlingMove(true, Squares.get(7, 5), Squares.get(7, 6));
        }
    }

    private void getCastlingMove(boolean kingSide, Square... empties) {
        Predicate<Square> emptyCheck = square -> board.isSquareEmpty(square);
        if (board.isCastlingAllowed(kingSide) && Arrays.stream(empties).allMatch(emptyCheck)) {
            specialMoves.add(new CastlingMove(kingSide));
        }
    }

    private void getEnPassantMoves() {
        if (board.getEnPassantSquare() != null) {
            getEnPassantMove(-side, -1);
            getEnPassantMove(-side, 1);
        }
    }

    private void getEnPassantMove(int rowOffset, int colOffset) {
        if (board.getEnPassantSquare().isOffsetOnBoard(rowOffset, colOffset)) {
            Square startSquare = board.getEnPassantSquare().getOffsetSquare(rowOffset, colOffset);
            char piece = (side == 1) ? 'P' : 'p';
            if (board.getPiece(startSquare) == piece) {
                specialMoves.add(new EnPassantMove(startSquare));
            }
        }
    }

    private void addNormalMove(Square startSquare, Square endSquare) {
        if (endSquare.getIndex() == 0 && board.getPiece(startSquare) == 'p') {
            board.printBoard();
            System.out.println("Moves.addNormalMove");
        }
        normalMoves.add(new NormalMove(startSquare, endSquare));
        attackedSquares.add(endSquare);
    }

    private void addAttackMove(Square startSquare, Square endSquare, char targetPiece) {
        attackMoves.add(new AttackMove(startSquare, endSquare, targetPiece));
        attackedSquares.add(endSquare);
    }

    private void addDefendMove(Square startSquare, Square endSquare, char targetPiece) {
        defendMoves.add(new DefendMove(startSquare, endSquare, targetPiece));
    }

    public List<Move> getNormalMoves() {
        return normalMoves;
    }

    public List<Move> getAttackMoves() {
        return attackMoves;
    }

    public List<DefendMove> getDefendMoves() {
        return defendMoves;
    }

    public List<Move> getSpecialMoves() {
        return specialMoves;
    }

    public Set<Square> getAttackedSquares() {
        return attackedSquares;
    }

    public List<Move> getAllPossibleMoves() {
        List<Move> allMoves = new ArrayList<>();
        allMoves.addAll(normalMoves);
        allMoves.addAll(attackMoves);
        allMoves.addAll(specialMoves);
        return allMoves;
    }
}
