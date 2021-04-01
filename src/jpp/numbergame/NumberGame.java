package jpp.numbergame;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NumberGame {

    private final int width;
    private final int height;
    private int points;
    private final Tile[][] tiles;

    public NumberGame(int width, int height) {
        this.points = 0;
        tiles = new Tile[width][height];
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = null;
            }
        }
    }

    public NumberGame(int width, int height, int initialTiles) {
        this.points = 0;
        if (initialTiles < 0 || initialTiles > width * height || width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;
        tiles = new Tile[width][height];

        for (int i = 0; i < initialTiles; i++) {
            addRandomTile();
        }

    }

    public int get(Coordinate2D coord) {
        if (tiles[coord.getX()][coord.getY()] == null) {
            return 0;
        }
        if (coord.getX() >= width || coord.getY() >= height || coord.getX() < 0 || coord.getY() < 0) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[coord.getX()][coord.getY()].getValue();
    }

    public int get(int x, int y) {
        if (tiles[x][y] == null) {
            return 0;
        }

        if (x >= width || y >= height) {
            throw new IndexOutOfBoundsException();
        }

        return tiles[x][y].getValue();
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isFull() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Tile addRandomTile() {
        if (isFull()) {
            throw new TileExistsException();
        }
        Random random = new Random();
        int x_random = random.nextInt(width);
        int y_random = random.nextInt(height);
        int value_random = random.nextInt(101);

        while (tiles[x_random][y_random] != null) {
            x_random = random.nextInt(width);
            y_random = random.nextInt(height);
        }

        if (value_random <= 10) {
            addTile(x_random, y_random, 4);
            return tiles[x_random][y_random];
        } else {
            addTile(x_random, y_random, 2);
            return tiles[x_random][y_random];
        }
    }

    public Tile addTile(int x, int y, int value) {
        if (tiles[x][y] != null) {
            throw new TileExistsException();
        }
        tiles[x][y] = new Tile(new Coordinate2D(x, y), value);
        return tiles[x][y];
    }

    public List <Move> move(Direction dir) {
        List <Move> moveList = new LinkedList <>();
        int toVerify;
        boolean haveTiles = false;

        if (dir == Direction.DOWN) {
            for (int i = 0; i < width; i++) {
                toVerify = height - 1;
                for (int j = height - 2; j >= 0; j--) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k > j; k--) {
                            for (int l = j + 1; l < k; l++) {
                                if (tiles[i][l] != null) {
                                    haveTiles = true;
                                }
                            }
                            if (tiles[i][k] == null) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(i, k), tiles[i][j].getValue()));
                                this.addTile(i, k, tiles[i][j].getValue());
                                tiles[i][j] = null;
                                toVerify = k;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[i][k].getValue() && !haveTiles) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(i, k), tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[i][k] = new Tile(new Coordinate2D(i, k), 2 * tiles[i][j].getValue());
                                this.points += 2 * tiles[i][j].getValue();
                                tiles[i][j] = null;
                                toVerify = k - 1;
                                break;
                            }
                            if (k - 1 == j) {
                                toVerify = j;
                            }
                            haveTiles = false;
                        }
                    }

                }
            }
            return moveList;
        } else if (dir == Direction.UP) {
            for (int i = 0; i < width; i++) {
                toVerify = 0;
                for (int j = 1; j < height; j++) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k < j; k++) {
                            for (int l = k + 1; l < j; l++) {
                                if (tiles[i][l] != null) {
                                    haveTiles = true;
                                    break;
                                }
                            }
                            if (tiles[i][k] == null) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(i, k), tiles[i][j].getValue()));
                                this.addTile(i, k, tiles[i][j].getValue());
                                tiles[i][j] = null;
                                toVerify = k;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[i][k].getValue() && !haveTiles) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(i, k), tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[i][k] = new Tile(new Coordinate2D(i, k), 2 * tiles[i][j].getValue());
                                this.points += 2 * tiles[i][j].getValue();
                                tiles[i][j] = null;
                                toVerify = k + 1;
                                break;
                            }
                            if (k - 1 == j) {
                                toVerify = j;
                            }
                            haveTiles = false;
                        }
                    }

                }
            }
            return moveList;

        } else if (dir == Direction.RIGHT) {
            for (int j = 0; j < height; j++) {
                toVerify = width - 1;
                for (int i = width - 2; i >= 0; i--) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k > i; k--) {
                            for (int l = i + 1; l < k; l++) {
                                if (tiles[l][j] != null) {
                                    haveTiles = true;

                                }
                            }
                            if (tiles[k][j] == null) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(k, j), tiles[i][j].getValue()));
                                this.addTile(k, j, tiles[i][j].getValue());
                                tiles[i][j] = null;
                                toVerify = k;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !haveTiles) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(k, j), tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[k][j] = new Tile(new Coordinate2D(k, j), 2 * tiles[i][j].getValue());
                                this.points += 2 * tiles[i][j].getValue();
                                tiles[i][j] = null;
                                toVerify = k - 1;
                                break;
                            }
                            if (k - 1 == i) {
                                toVerify = i;
                            }
                            haveTiles = false;
                        }
                    }

                }
            }
            return moveList;

        } else {
            for (int j = 0; j < height; j++) {
                toVerify = 0;
                for (int i = 1; i < width; i++) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k < i; k++) {
                            for (int l = k + 1; l < i; l++) {
                                if (tiles[l][j] != null) {
                                    haveTiles = true;
                                }
                            }
                            if (tiles[k][j] == null) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(k, j), tiles[i][j].getValue()));
                                this.addTile(k, j, tiles[i][j].getValue());
                                tiles[i][j] = null;
                                toVerify = k;
                                break;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !haveTiles) {
                                moveList.add(new Move(new Coordinate2D(i, j), new Coordinate2D(k, j), tiles[i][j].getValue(), 2 * tiles[i][j].getValue()));
                                tiles[k][j] = new Tile(new Coordinate2D(k, j), 2 * tiles[i][j].getValue());
                                this.points += 2 * tiles[i][j].getValue();
                                tiles[i][j] = null;
                                toVerify = k + 1;
                                break;
                            }
                            if (k - 1 == i){
                                toVerify = i;
                            }
                            haveTiles = false;
                        }
                    }

                }
            }
            return moveList;
        }
    }

    public boolean canMove(Direction dir) {

        int toVerify;
        boolean haveTiles = false;
        if (dir == Direction.DOWN) {
            for (int i = 0; i < width; i++) {
                toVerify = height - 1;
                for (int j = height - 2; j >= 0; j--) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k > j; k--) {
                            for (int l = j + 1; l < k; l++) {
                                if (tiles[i][j] != null) {
                                    haveTiles = true;
                                }
                            }
                            if (tiles[i][k] == null) {
                                return true;
                            } else {
                                assert tiles[i][j] != null;
                                if (tiles[i][j].getValue() == tiles[i][k].getValue() && !haveTiles) {
                                    return true;
                                }
                            }
                            if (k - 1 == j) {
                                toVerify = j;
                            }
                            haveTiles = false;
                        }
                    }
                }
            }

        } else if (dir == Direction.UP) {
            for (int i = 0; i < width; i++) {
                toVerify = 0;
                for (int j = 1; j < height; j++) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k < j; k++) {
                            for (int l = k + 1; l < j; l++) {
                                if (tiles[i][l] != null) {
                                    haveTiles = true;
                                }
                            }
                            if (tiles[i][k] == null) {
                                return true;
                            } else if (tiles[i][j].getValue() == tiles[i][k].getValue() && !haveTiles) {
                                return true;
                            }
                            if (k - 1 == j){
                                toVerify = j;
                            }
                            haveTiles = false;
                        }
                    }
                }
            }

        } else if (dir == Direction.RIGHT) {
            for (int j = 0; j < height; j++) {
                toVerify = width - 1;
                for (int i = width - 2; i >= 0; i--) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k > i; k--) {
                            for (int l = i + 1; l < k; l++) {
                                if (tiles[l][j] != null) {
                                    haveTiles = true;
                                    break;
                                }
                            }
                            if (tiles[k][j] == null) {
                                return true;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !haveTiles) {
                                return true;
                            }
                            if (k - 1 == i) {
                                toVerify = i;
                            }
                            haveTiles = false;
                        }
                    }
                }
            }
        } else {
            for (int j = 0; j < height; j++) {
                toVerify = 0;
                for (int i = 1; i < width; i++) {
                    if (tiles[i][j] != null) {
                        for (int k = toVerify; k < i; k++) {
                            for (int l = k + 1; l < i; l++) {
                                if (tiles[l][j] != null) {
                                    haveTiles = true;
                                    break;
                                }
                            }
                            if (tiles[k][j] == null) {
                                return true;
                            } else if (tiles[i][j].getValue() == tiles[k][j].getValue() && !haveTiles) {
                                return true;
                            }
                            if (k - 1 == i) {
                                toVerify = i;
                            }
                            haveTiles = false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canMove() {
        return (canMove(Direction.UP) || canMove(Direction.RIGHT) || canMove(Direction.DOWN) || canMove(Direction.LEFT));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            stringBuilder.append("|");
            for (int j = 0; j < tiles[i].length; j++) {
                stringBuilder.append(get(j, i)).append("|");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        List <Move> moveList;
        NumberGame numberGame = new NumberGame(4, 4);
        numberGame.addTile(0, 3, 2);
        numberGame.addTile(1, 3, 2);
        numberGame.addTile(2, 3, 2);
        numberGame.addTile(1, 2, 2);
        numberGame.addTile(1, 1, 4);
        numberGame.addTile(2, 2, 2);
        numberGame.addTile(0, 0, 4);
        numberGame.addTile(0, 1, 8);
        numberGame.addTile(0, 2, 8);

        System.out.println(numberGame.toString());
        moveList = numberGame.move(Direction.LEFT);
        System.out.println(numberGame.toString());
        System.out.println(numberGame.canMove(Direction.LEFT));

        for (int i = 0; i < moveList.size(); i++) {
            System.out.println(moveList);
        }

        NumberGame game = new NumberGame(3, 3, 9);
        System.out.println(game.toString());
        System.out.println(game.isFull());
        System.out.println(game.canMove());
    }
}
