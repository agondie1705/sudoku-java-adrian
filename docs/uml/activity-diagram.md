```markdown
# UML — Activity Diagram (Game Flow)

```mermaid
flowchart TD

A[Start Game] --> B[Generate Full Board]
B --> C[Apply Difficulty Mask]
C --> D[Show Board in UI]

D --> E[User Inputs Number]
E --> F{Correct?}

F -->|Yes| G[Update Board]
G --> H{Board Complete?}
H -->|Yes| I[Show "HAS GANADO"]
H -->|No| D

F -->|No| J[Add Error]
J --> K{Errors >= 3?}
K -->|Yes| L[Show "GAME OVER"]
K -->|No| D
