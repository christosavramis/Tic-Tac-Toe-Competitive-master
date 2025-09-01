class GameAPI {
    board = [
        ['', '', ''],
        ['', '', ''],
        ['', '', '']
    ];

    async playMove(placement) {
        this.board[placement.y][placement.x] = 'O';

        let randomMove;
        for (let x = 0; x < 3; x++) {
            if (randomMove) break;
            for (let y = 0; y < 3; y++) {
                if (randomMove) break;
                const tempRandomMove = {
                    x: Math.floor(Math.random() * 3),
                    y: Math.floor(Math.random() * 3)
                };
                if (this.board[tempRandomMove.y][tempRandomMove.x] === '') {
                    randomMove = tempRandomMove;
                }
            }
        }
        if (!randomMove) {
            // board is full, it's a tie
            return {
                board: this.board,
                winner: null,
                draw: true
            };
        }
        this.board[randomMove.y][randomMove.x] = 'X';

        let winningSymbol = null;
        for (let x = 0; x < 3; x++) {
            for (let y = 0; y < 3; y++) {
                const symbol = this.board[y][x];
                if (!symbol || symbol === '') continue;
                // Check horizontal
                if (this.board[y][(x + 1) % 3] === symbol && this.board[y][(x + 2) % 3] === symbol) {
                    winningSymbol = symbol;
                }
                // Check vertical
                if (this.board[(y + 1) % 3][x] === symbol && this.board[(y + 2) % 3][x] === symbol) {
                    winningSymbol = symbol;
                }
                // Check diagonal
                if (x === y) {
                    if (this.board[(y + 1) % 3][(x + 1) % 3] === symbol && this.board[(y + 2) % 3][(x + 2) % 3] === symbol) {
                        winningSymbol = symbol;
                    }
                }
                if (x + y === 2) {
                    if (this.board[(y + 1) % 3][(x - 1 + 3) % 3] === symbol && this.board[(y + 2) % 3][(x - 2 + 3) % 3] === symbol) {
                        winningSymbol = symbol;
                    }
                }
            }
        }

        // simulate a wait for server response
        await new Promise(resolve => setTimeout(resolve, 500));

        return {
            board: this.board,
            winner: winningSymbol
        };
    }
}
const GAME_API = new GameAPI();
export { GAME_API };