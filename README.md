# SP

SP (syntactic parser)
1) Необходимо добавить возможность заменять модули (Gama, parser, JMorfSdk)
2) Внешнее api - на вход строка, на выходе структура
3) Разбить на получение стрктуры для опорных оборотов, предложений, абзаца и текста

##### Пример:
```
SyntaxParser sp = new SyntaxParser();
sp.init();
List<BearingPhraseSP> phrase
        = sp.getTreeSentence("Стало ясно, что будет с российской валютой.");
phrase.forEach(System.out::println);
BearingPhraseSP{
    words=[
        WordSP=[currencyForm={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, main=null, dependents=[{isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}]], 
        WordSP=[currencyForm={isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}, main={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, dependents=[]]
        ],
    mainOmoForm=[currencyForm={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, main=null, dependents=[{isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}]]
}
```
#### Вывод
```
BearingPhraseSP{
	words=[
		WordSP=[currencyForm={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, main=null, dependents=[{isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}]], 
		WordSP=[currencyForm={isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}, main={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, dependents=[]]
		],
	mainOmoForm=[currencyForm={isInit=false,hash=758268027,стало,ToS=20,morf=669740}, main=null, dependents=[{isInit=true,hash=96318212,ясно,ToS=9,morf=8388608}]]
}
BearingPhraseSP{
	words=[
		WordSP=[currencyForm={isInit=true,hash=93560832,что,ToS=30,morf=108}, main=null, dependents=[], currencyForm={isInit=true,hash=93560832,что,ToS=13,morf=0}, main=null, dependents=[], currencyForm={isInit=true,hash=93560832,что,ToS=9,morf=1048576}, main=null, dependents=[], currencyForm={isInit=false,hash=837844480,что,ToS=30,morf=556}, main=null, dependents=[], currencyForm={isInit=true,hash=93560832,что,ToS=14,morf=0}, main=null, dependents=[]], 
		WordSP=[currencyForm={isInit=false,hash=200326010,будет,ToS=20,morf=785440}, main=null, dependents=[{isInit=true,hash=77266944,с,ToS=12,morf=0}]], 
		WordSP=[currencyForm={isInit=true,hash=77266944,с,ToS=12,morf=0}, main={isInit=false,hash=200326010,будет,ToS=20,morf=785440}, dependents=[{isInit=false,hash=202458017,валютой,ToS=17,morf=363}]], 
		WordSP=[currencyForm={isInit=false,hash=709040926,российской,ToS=18,morf=360}, main={isInit=false,hash=202458017,валютой,ToS=17,morf=363}, dependents=[]], 
		WordSP=[currencyForm={isInit=false,hash=202458017,валютой,ToS=17,morf=363}, main={isInit=true,hash=77266944,с,ToS=12,morf=0}, dependents=[{isInit=false,hash=709040926,российской,ToS=18,morf=360}]]
	],
	mainOmoForm=[currencyForm={isInit=false,hash=200326010,будет,ToS=20,morf=785440}, main=null, dependents=[{isInit=true,hash=77266944,с,ToS=12,morf=0}]]
}

```