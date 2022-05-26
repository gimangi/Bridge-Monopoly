# Use Case Specifications

BridgeMonopolyGame의 Use case 명세서 모음입니다.



## Index

1. [**UC101: Start the Game**](#uc101:-start-the-game)

2. [**UC102: Load Map**](#uc102:-load-map)
3. [**UC103: Initialize Players**](#uc103:-initialize-players)
4. [**UC201: Proceed a Turn**](#uc201:-proceed-a-turn)

5. [**UC202: Stay Turn**](#uc202:-stay-turn)
6. [**UC203: Move Piece Turn**](#uc203:-move-piece-turn)
7. [**UC204: Roll a Dice**](#uc204:-roll-a-dice)
8. [**UC205: Combine Direction**](#uc205:-combine-direction)
9. [**UC206: Arrive at the End**](#uc206:-arrive-at-the-end)



## Use Cases

### UC101: Start the Game

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Start the Game                                               |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Commander                                                    |
| Stakeholders and Interests          | Commander : 진행할 게임에 사용할 맵과 플레이어 수를 정하고자 한다.</br>Player : 결정된 플레이어 수에 따라 번갈아 가며 턴을 수행하고자 한다.</br>Map Data : 게임에 사용할 맵을 제공해야 한다. |
| Preconditions                       | Commander는 사용할 맵 파일의 이름을 알고 있다.               |
| Success Guarantee                   | 두 개 이상의 셀을 가지는 맵을 불러온다.</br>Player의 수는 2~4 범위에서 결정된다.</br>결정한 수만큼 Player를 생성한다. |
| Main Success Scenario               | 1. Commander는 맵 파일의 이름을 입력한다.</br>2. include UC102 "Load Map".</br>3. Commander는 플레이어 수를 입력한다.</br>4. include UC103 "Initialize Players". |
| Extensions                          | 1a. 해당하는 맵을 찾을 수 없는 경우</br> 1. 맵 파일 이름을 다시 입력 받는다.</br>2a. 입력된 셀의 개수가 2개 미만인 경우</br> 1. 오류 메시지를 표시하고 다시 입력 받는다.</br>3a. Player 수가 2~4 범위가 아닌 경우</br> 1. 다시 입력 받는다. |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 게임을 실행할 경우 처음 한 번 일어난다.                      |
| Miscellaneous                       | -                                                            |



### UC102: Load Map

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Load Map                                                     |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Commander                                                    |
| Stakeholders and Interests          | Commander : 게임에 사용할 맵 데이터를 불러오고자 한다.</br>Map Data : 게임에 사용할 맵을 제공해야 한다. |
| Preconditions                       | 올바른 맵 정보를 입력 받은 상태이다.                         |
| Success Guarantee                   | 모든 셀의 정보를 불러온다.</br>각 셀끼리 연결된 방향의 정보를 가지게 된다.</br>각 셀은 종류 또는 아이템 정보를 가지게 된다. |
| Main Success Scenario               | 1. Map Data는 맵 정보 중 한 줄을 입력한다.</br>2. 첫 글자를 이용해 셀의 종류를 판단한다.</br>3. 현재 입력의 두 번째 글자가 나타내는 방향으로 현재 셀에 이전 셀을 연결한다.</br>4. 이전 입력의 마지막 글자가 나타내는 방향으로 이전 셀에 현재 셀을 연결한다.</br>5. 스탭 1~4를 반복한다.</br>6. Bridge 셀과 인접한 셀을 연결한다. |
| Extensions                          | 2a. 첫 글자가 'S'인 경우</br> 1. Start 셀로 결정한다.</br>2b. 첫 글자가 'E'인 경우</br> 1. End 셀로 결정한다.</br>2c. 첫 글자가 'C'인 경우</br> 1. 일반 셀로 결정한다.</br>2d. 첫 글자가 'B'인 경우</br> 1. Bridge 셀로 결정한다.</br>2e. 첫 글자가 'H'인 경우</br> 1. Hammar 셀로 결정한다.</br>2f. 첫 글자가 'S'인 경우</br> 1. Saw 셀로 결정한다.</br>2g. 첫 글자가 'P'인 경우</br> 1. Philips Driver 셀로 결정한다.</br>3a. 현재 Start 또는 End 셀인 경우</br> 1. 해당 스텝을 건너뛴다.</br>4a. 현재 Start 셀인 경우</br> 1. 해당 스텝을 건너뛴다. |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 게임마다 최대 한 번 일어난다.                                |
| Miscellaneous                       | -                                                            |



### UC103: Initialize Players

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Initialize Players                                           |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Commander                                                    |
| Stakeholders and Interests          | Commander : 선택한 플레이어 수를 이용해 게임을 진행하고자 한다.</br>Player : 처음 선택된 플레이어 수만큼 턴을 분배 받고자 한다. |
| Preconditions                       | 올바른 플레이어 수를 입력받은 상태이다.                      |
| Success Guarantee                   | 플레이어를 생성한다.                                         |
| Main Success Scenario               | 1. 플레이어 수만큼 Player를 생성한다.</br>2. 모든 Player의 Piece의 위치를 Start 셀에 위치한다.</br>3. 현재 턴을 Player 1로 설정한다.</br>4. 모든 Player 및 Piece를 화면에 표시한다. |
| Extensions                          | -                                                            |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 게임을 실행할 경우 처음 한 번 일어난다.                      |
| Miscellaneous                       | -                                                            |



### UC201: Proceed a Turn

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | {abstract}</br>Proceed a Turn                                |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 자신의 턴을 진행하고자 한다.                        |
| Preconditions                       | 게임은 초기화 완료된 상태이다.</br>현재 Player의 턴이다.     |
| Success Guarantee                   | Player는 원하는 행동을 완료하고 턴을 넘겨준다.               |
| Main Success Scenario               | 1. (abstract) Player가 선택한 행동을 진행한다.</br>2. 턴을 다음 Player에게 넘긴다. |
| Extensions                          | -                                                            |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 게임이 종료될 때까지 매 턴마다 실행된다.                     |
| Miscellaneous                       | -                                                            |



### UC202: Stay Turn

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Stay Turn                                                    |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 현재 턴에 말을 움직이지 않고 쉬고자 한다.           |
| Preconditions                       | Player는 End 셀에 도달하지 않았다.                           |
| Success Guarantee                   | Player의 다리 카드가 감소한다.                               |
| Main Success Scenario               | 1. Player의 다리 카드를 감소한다.</br>2. generalization UC 201 "Proceed a Turn" |
| Extensions                          | 1a. Player의 다리 카드 수가 0인 경우</br> 1. 스텝을 건너뛴다. |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 턴마다 최대 한 번 일어난다.                                  |
| Miscellaneous                       | -                                                            |



### UC203: Move Piece Turn

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Move Piece Turn                                              |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 현재 턴에 주사위를 굴려서 말을 이동하고자 한다.     |
| Preconditions                       | Player는 End 셀에 도달하지 않았다.                           |
| Success Guarantee                   | Player의 Piece가 선택한 위치로 이동한다.                     |
| Main Success Scenario               | 1. include UC 204: "Roll a Dice".</br>2. include UC 205: "Combine Direction"</br>3. 해당하는 위치로 Piece를 이동시킨다.</br> 4. generalization UC 201 "Proceed a Turn" |
| Extensions                          |                                                              |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 턴마다 최대 한 번 일어난다.                                  |
| Miscellaneous                       | -                                                            |



### UC204: Roll a Dice

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Roll a Dice                                                  |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 주사위를 굴려 나온 수를 이용해 말을 이동하고자 한다. |
| Preconditions                       | -                                                            |
| Success Guarantee                   | 주사위 결과로 1~6의 수를 얻는다.                             |
| Main Success Scenario               | 1. Player는 주사위 굴리기를 시작하는 입력을 한다. </br>2. 1~6 범위의 무작위 수를 생성한다.</br>3. 결과를 표시한다. |
| Extensions                          | -                                                            |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 턴마다 최대 한 번 일어난다.                                  |
| Miscellaneous                       | -                                                            |



### UC205: Combine Direction

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Combine Direction                                            |
| Scope                               | System use case                                              |
| Level                               | User-goal                                                    |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 주사위를 굴려 나온 수를 이용해 말을 이동하고자 한다. |
| Preconditions                       | -                                                            |
| Success Guarantee                   | 주사위 결과로 이동할 수 있는 위치 중에서 Player의 선택을 알아낸다. |
| Main Success Scenario               | 1. Player는 이동할 방향 조합을 입력한다. </br>2. 현재 위치에서 입력된 방향을 이용해 다음 위치를 찾는다 .</br>3. 모든 방향에 대해 스텝 2를 반복한다. |
| Extensions                          | 1a. 입력한 방향의 수가 주사위 수와 일치하지 않는 경우</br> 1.  올바르지 않은 입력이라고 표시한다.</br> 2. 해당 스탭을 다시 실행한다. 2a. 해당 방향에 셀이 없는 경우</br> 1. 불가능한 방향이라고 표시한다.</br> 2. 스탭 1로 돌아간다.</br>2b. 뒤로 가는 방향이고, End 셀에 도착한 플레이어가 있는 경우</br> 1. 뒤로 갈 수 없다고 표시한다.</br> 2. 스탭 1로 돌아간다.</br>2c. End 셀일 경우</br> 1. 모든 스탭을 건너뛴다. |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 턴마다 최대 한 번 일어난다.                                  |
| Miscellaneous                       | -                                                            |



### UC206: Arrive at the End

| Use Case Section                    | Comment                                                      |
| ----------------------------------- | ------------------------------------------------------------ |
| Use Case Name                       | Arrive at the End                                            |
| Scope                               | System use case                                              |
| Level                               | Subfunction                                                  |
| Primary Actor                       | Player                                                       |
| Stakeholders and Interests          | Player : 도착지에 도달해서 스코어를 정산한다.                |
| Preconditions                       | extend UC203 "Move Piece Turn" -> Player의 Piece가 End 셀로 이동한 경우 |
| Success Guarantee                   | 해당 Player의 스코어를 정산하고 이후 다른 플레이어는 뒤로 갈 수 없다. |
| Main Success Scenario               | 1. Player의 최종 점수를 표시한다. </br>2. Player가 더 이상 턴을 가지지 않도록 변경한다.</br>3. 다음 턴부터 다른 Player는 뒤로 이동할 수 없도록 변경한다. |
| Extensions                          | 3a. 이미 End 셀에 도착한 Player가 존재할 경우</br> 1. 해당 스탭을 건너뛴다. |
| Special Requirements                | -                                                            |
| Technology and Data Variations List | -                                                            |
| Frequency of Occurrence             | 턴마다 최대 한 번 일어난다.                                  |
| Miscellaneous                       | -                                                            |

