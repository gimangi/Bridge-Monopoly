# Operation Contracts

BridgeMonopolyGame의 Operation Contracts를 분석한 결과입니다.



## Index

1. [**startNewGame**](#startnewgame)

2. [**loadMapFile**](#loadmapfile)
3. [**enterNumOfPlayers**](#enternumofplayers)
4. [**proceedTurn**](#proceedturn)
5. [**reducePenaltyCard**](#reducepenaltycard)
6. [**movePiece**](#movepiece)

7. [**arriveEnd**](#arriveend)



## Operation Contracts list

 ### startNewGame

| Operation        | startNewGame()                                               |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Start the Game                                    |
| Preconditions    | -                                                            |
| Postconditions   | Turn의 인스턴스인 turn이 생성된다. (instance creation)</br>Board의 인스턴스인 board가 생성된다. (instance creation)</br>DirectionCombiner의 인스턴스인 directionCombiner가 생성된다. (instance creation)</br>맵 정보의 한 줄마다 Cell의 인스턴스인 cell이 생성된다. (instance creation)</br>각 cell은 자신과 인접한 cell을 가리킨다. (attribute modification)</br>board에 cell을 추가한다. (attribute modification) |



 ### loadMapFile

| Operation        | loadMapFile(fileName: String)                                |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Load Map                                          |
| Preconditions    | 사용자는 맵 파일 목록이 없어도 파일 이름을 알고 있다.        |
| Postconditions   | Board의 인스턴스인 board가 생성되었다. (instance creation)</br>맵 정보의 한 줄마다 Cell의 인스턴스인 cell이 생성되었다. (instance creation)</br>각 cell은 자신과 인접한 cell을 가리키게 되었다. (attribute modification)</br>board에 cell을 추가했다. (association formed) |



 ### enterNumOfPlayers

| Operation        | enterNumOfPlayers(num: integer)                              |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Initialize Players                                |
| Preconditions    | -                                                            |
| Postconditions   | 입력 개수만큼 Player의 인스턴스 player를 생성되었다. (instance creation)</br>각 player는 생성된 순서별로 id를 가지게 되었다. (attribute modification)</br>turn은 id가 1인 player를 가리키도록 초기화 되었다. (attribute modification)</br>player 수만큼 Piece의 인스턴스인 piece가 생성되었다. (instance creation)</br>각 player는 piece와 연결되었다. (association formed) |



 ### proceedTurn

| Operation        | proceedTurn(behavior: TurnType)                              |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Proceed a Turn                                    |
| Preconditions    | player는 End 셀에 도달하지 않았다.                           |
| Postconditions   | turn은 다음 player를 가리키게 되었다. (attribute modification) |



 ### reducePenaltyCard

| Operation        | reducePenaltyCard()                                          |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Stay Turn                                         |
| Preconditions    | actor Player는 turnType으로 'stay'를 선택하였다.             |
| Postconditions   | player의 penaltyCards가 1만큼 감소되었다. (attribute modification) |



 ### movePiece

| Operation        | movePiece(dest: Cell)                                        |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Move Piece Turn                                   |
| Preconditions    | actor Player는 turnType으로 'move'를 선택하였다.             |
| Postconditions   | player와 연관된 piece의 위치를 dest로 변경한다. (attribute modification) |



 ### arriveEnd

| Operation        | arriveEnd()                                                  |
| ---------------- | ------------------------------------------------------------ |
| Cross References | Use cases: Move Piece Turn                                   |
| Preconditions    | dest가 End 셀이다.                                           |
| Postconditions   | player는 더 이상 턴을 가지지 않는다. (attribute modification)</br>directionCombiner는 앞으로 뒤로 가는 입력을 받을 수 없다. (attribute modification) |

