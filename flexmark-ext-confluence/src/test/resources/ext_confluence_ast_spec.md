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

```````````````````````````````` example Basic Tests: 5
> test a simple
> blockquote
.
{quote}
test a simple blockquote
{quote}

.
Document[0, 29]
  BlockQuote[0, 29] marker:[0, 1, ">"]
    Paragraph[2, 29]
      Text[2, 15] chars:[2, 15, "test  … imple"]
      SoftLineBreak[15, 16]
      Text[18, 28] chars:[18, 28, "blockquote"]
````````````````````````````````



### Blocks with title

```````````````````````````````` example Blocks with title: 1
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

```````````````````````````````` example Blocks with title: 2
> **info:** It's a very _complex_ title with юникод and numbers 1,2,3...
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

```````````````````````````````` example Blocks with title: 3
> **tip:** title with vertical bar | 123
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

### Complex content

```````````````````````````````` example Complex content: 1
> **info:** Aboutme
> 
>> Lorem ipsum dolor sit amet, 
>> consectetur adipiscing elit
>
.
{info}
{quote}
Lorem ipsum dolor sit amet, consectetur adipiscing elit
{quote}

{info}

.
Document[0, 88]
  ConfluenceBlock[4, 86] type:[4, 8, "info"] title:[12, 19, "Aboutme"]
    BlockQuote[20, 86] marker:[20, 21, ">"]
      BlockQuote[24, 86] marker:[24, 25, ">"]
        Paragraph[26, 86]
          Text[26, 53] chars:[26, 53, "Lorem … amet,"]
          SoftLineBreak[54, 55]
          Text[58, 85] chars:[58, 85, "conse …  elit"]
````````````````````````````````

```````````````````````````````` example Complex content: 1
> **warning:** About him
> 
> Lorem ipsum dolor sit amet, 
>> consectetur adipiscing elit
>
> - one
> - two
> 
> have a **strong** and _pure_ feeling

.
{warning:title=About him}
Lorem ipsum dolor sit amet,

{quote}
consectetur adipiscing elit
{quote}

* one
* two
have a *strong* and _pure_ feeling

{warning}

.
Document[0, 151]
  ConfluenceBlock[4, 150] type:[4, 11, "warning"] title:[15, 24, "About him"]
    BlockQuote[25, 150] marker:[25, 26, ">"]
      Paragraph[30, 59]
        Text[30, 57] chars:[30, 57, "Lorem … amet,"]
      BlockQuote[60, 90] marker:[60, 61, ">"]
        Paragraph[62, 90]
          Text[62, 89] chars:[62, 89, "conse …  elit"]
      BulletList[94, 108] isTight
        BulletListItem[94, 100] open:[94, 95, "-"] isTight
          Paragraph[96, 100]
            Text[96, 99] chars:[96, 99, "one"]
        BulletListItem[102, 108] open:[102, 103, "-"] isTight hadBlankLineAfter
          Paragraph[104, 108] isTrailingBlankLine
            Text[104, 107] chars:[104, 107, "two"]
      Paragraph[113, 150]
        Text[113, 120] chars:[113, 120, "have a "]
        StrongEmphasis[120, 130] textOpen:[120, 122, "**"] text:[122, 128, "strong"] textClose:[128, 130, "**"]
          Text[122, 128] chars:[122, 128, "strong"]
        Text[130, 135] chars:[130, 135, " and "]
        Emphasis[135, 141] textOpen:[135, 136, "_"] text:[136, 140, "pure"] textClose:[140, 141, "_"]
          Text[136, 140] chars:[136, 140, "pure"]
        Text[141, 149] chars:[141, 149, " feeling"]
````````````````````````````````