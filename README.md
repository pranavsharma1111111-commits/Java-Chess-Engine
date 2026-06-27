# Java Chess Engine ♟️

A chess engine I built from scratch in Java. This started as a curiosity project — I wanted to understand how chess engines actually think. Ended up being one of the most challenging and rewarding things I've built.

---

## What it can do

- Plays legal, complete chess against a human player
- Uses **Minimax search with Alpha-Beta pruning** to look ahead up to depth 6
- **Move ordering** to maximize pruning efficiency (better moves searched first)
- **Iterative deepening** — progressively searches deeper within a time budget
- Highlights legal moves when you click a piece
- Shows the last move made on the board
- Clean GUI built with Java Swing, visually inspired by Chess.com

---

## How the AI actually works

The engine doesn't "know" chess strategy the way a human does. It works by brute-force searching through possible future positions and picking the move that leads to the best outcome assuming both sides play optimally.

**Search — Minimax + Alpha-Beta:**
At each position, the engine generates all legal moves, plays them out mentally, and evaluates the resulting position. It does this recursively up to 6 moves deep. Alpha-beta pruning cuts off branches that can't possibly affect the final decision — this makes the search dramatically faster without changing the result.

**Move ordering** makes alpha-beta much more effective. By searching promising moves first (captures, checks), the engine prunes more branches early and reaches deeper in the same time budget.

**Iterative deepening** means the engine searches depth 1, then depth 2, then depth 3, and so on — completing each full depth before going deeper. This gives a usable move at any point and helps with move ordering at higher depths.

**Evaluation — material + basic positional:**
Right now the engine evaluates positions primarily by material count (standard piece values) with some positional adjustments for knights. Simple, but enough to play at 1850 strength.

---

### Proof — vs 1850 bot (31 May 2026)

The engine played as Black with no opening book — meaning it pushed pawns randomly for the first 8 moves and walked straight into a theoretically lost position by move 15. White had a bishop on e8, a knight on e6, and a powerful attack. A human in that position would have resigned.

The engine didn't. It calculated its way out tactically, ground through a complex endgame, and won by checkmate on move 61.

That is actually the more interesting result — it didn't win from a clean position. It recovered from a disaster through pure calculation.

> Full game PGN available in [`games/vs_1850_bot.pgn`](games/vs_1850_bot.pgn)
>
> *Note: The engine's moves were played manually on a human account. The account rating reflects human play — the engine has no account of its own.*

The biggest weakness right now is the opening — with no opening book, it enters the middlegame from a bad position every time. With a decent opening book, this engine would realistically compete with 2100+ bots where pure calculation takes over.

---

## Tech

- **Language:** Java
- **GUI:** Java Swing
- **Board representation:** 2D array
- **Search:** Minimax with Alpha-Beta pruning
- **Other:** Move ordering, iterative deepening, legal move generation

---

## Known limitations

I know what's missing. Listing them honestly rather than pretending they don't exist:

- **Pawn promotion** — auto-promotes to queen only (covers ~95% of real games, underpromotion is rare)
- **No threefold repetition** detection
- **No 50-move rule** enforcement
- **No opening book** — plays from scratch every game
- **Evaluation is mostly material** — no pawn structure, king safety, or piece mobility yet
- **No bitboard** — using a 2D array which is simpler but slower than bitboard representation
- **No endgame tablebase**

The engine plays strong enough to be a real opponent despite these gaps.

---

## What I learned

This project taught me more about algorithms and problem-solving than anything else I've built. A few things that genuinely surprised me:

- How much alpha-beta pruning changes things — the speedup over plain minimax is enormous
- Move ordering is almost as important as the search itself
- Evaluation functions are deceptively hard — material alone gets you surprisingly far but plateaus quickly
- 2D arrays feel natural but bitboards would make this significantly faster — the tradeoff is simplicity vs performance

---

## Running it

Requires **Java 8+** and NetBeans (the project is set up as a NetBeans project).

```bash
# Clone the repo
git clone https://github.com/pranavsharma1111111-commits/Java-Chess-Engine.git

# Open in NetBeans and run, or compile manually:
cd Java-Chess-Engine
javac -d build/classes src/chess/pkg1500/*.java
java -cp build/classes chess.pkg1500.Chess
```

---

## Screenshots

**Before** (early version):

<img width="796" height="802" alt="Screenshot 2026-05-26 203233" src="https://github.com/user-attachments/assets/98371ea2-22f3-4fb6-837d-2a8bf175d924" />


**After** (current version):

<img width="792" height="803" alt="Screenshot 2026-05-28 091134" src="https://github.com/user-attachments/assets/f504bb70-ccca-44ce-affa-dd176307d934" />


*Built this to understand how chess engines think. Turns out the answer is: very differently from humans, and that's what makes it interesting.*
