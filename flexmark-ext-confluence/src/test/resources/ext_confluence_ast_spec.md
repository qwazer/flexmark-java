---
title: Confluence Block Extension Spec
author: Anton Reshetnikov
version:
date: '2018-07-28'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---


### Basic Tests

```````````````````````````````` example Basic Tests: 1
> **info:**
> 
> Contents of my note
>
.
{info}
Contents of my note

{info}

.
Document[0, 39]
  ConfluenceBlock[4, 37] type:[4, 8, "info"]
    BlockQuote[12, 37] marker:[12, 13, ">"]
      Paragraph[17, 37] isTrailingBlankLine
        Text[17, 36] chars:[17, 36, "Conte …  note"]
````````````````````````````````

```````````````````````````````` example Basic Tests: 2
> **info:**
> 
> Contents of my note
>
.
{info}
Contents of my note

{info}

.
Document[0, 39]
  ConfluenceBlock[4, 37] type:[4, 8, "info"]
    BlockQuote[12, 37] marker:[12, 13, ">"]
      Paragraph[17, 37] isTrailingBlankLine
        Text[17, 36] chars:[17, 36, "Conte …  note"]
````````````````````````````````

```````````````````````````````` example Basic Tests: 3
> **TIP:**
> 
> Content of my tip
>
.
{tip}
Content of my tip

{tip}

.
Document[0, 36]
  ConfluenceBlock[4, 34] type:[4, 7, "TIP"]
    BlockQuote[11, 34] marker:[11, 12, ">"]
      Paragraph[16, 34] isTrailingBlankLine
        Text[16, 33] chars:[16, 33, "Conte … y tip"]
````````````````````````````````

```````````````````````````````` example Basic Tests: 4
> **Note:**
> 
> Content of my note
>
.
{note}
Content of my note

{note}

.
Document[0, 38]
  ConfluenceBlock[4, 36] type:[4, 8, "Note"]
    BlockQuote[12, 36] marker:[12, 13, ">"]
      Paragraph[17, 36] isTrailingBlankLine
        Text[17, 35] chars:[17, 35, "Conte …  note"]
````````````````````````````````



## Note with title

```````````````````````````````` example Note with title: 1
> **info:** Aboutme
> 
> Lorem ipsum dolor sit amet, 
> consectetur adipiscing elit
>
.
{info}
Lorem ipsum dolor sit amet, consectetur adipiscing elit

{info}

.
Document[0, 86]
  ConfluenceBlock[4, 84] type:[4, 8, "info"] title:[12, 19, "Aboutme"]
    BlockQuote[20, 84] marker:[20, 21, ">"]
      Paragraph[25, 84] isTrailingBlankLine
        Text[25, 52] chars:[25, 52, "Lorem … amet,"]
        SoftLineBreak[53, 54]
        Text[56, 83] chars:[56, 83, "conse …  elit"]
````````````````````````````````
