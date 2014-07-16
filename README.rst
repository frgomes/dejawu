*dejawu* is an exploratory tentative to provide Dojo Toolkit with ScalaJS


Quick guide for the impatient
=============================

::

    $ sbt update clean run
    $ firefox http://localhost:9000/purejs # for pure Javascript
    $ firefox http://localhost:9000/app    # for Scala / ScalaJS / scalatags / dejawu


* The first page is written in pure Javascript, employing Dojo Toolkit behind the
  scenes. This page is doomed to dissapear in future.

* The second page is written entirely in Scala and it is intended to become a
  replacement of the page written in Javascript, providing the same look and feel
  of the first page. It also employs Dojo Toolkit behind the scenes.


Current status
==============

Rewrite due to migration to scalatags 0.3.8 is going on.
* code generator is temporarily disabled
* everything builds and compiles fine with SBT 0.13.5 and Scala 2.11.1
* http://localhost:9000/app is temporarily down

  
What *dejawu* is about?
=======================

Imagine you would like to write a web application but you hate HTML/CSS/Javascript.
You would like to employ a high level programming language, with strong type checking
and sophisticated features, instead of the old, horrible and error prone Javascript.

Now it is possible. This is how:

Scala_ is a high level, object functional programming language with strong type checking.
You can write the server side of your website in Scala. No surprises up to this point.
But you can also write the client side in Scala.

ScalaJS_ is a compiler plugin for Scala. It's role is compiling Scala code and generate
Javascript code.

Scalatags_ provides common HTML and SVG tags on top of ScalaJS. In other words, you can
design web pages using something similar to HTML, but looking like Scala code and with
strong syntactic and type checking.

Dejawu_ provides wrappers to Dojo Toolkit widgets and integrates seemlessly with Scalatags
in a way that you can design single-page web applications which contain common HTML tags or
SVG tags but also contain widgets from the `Dojo Toolkit`_ package.

.. _Scala : http://scala-lang.org/
.. _ScalaJS : http://www.scala-js.org/
.. _Scalatags : http://github.com/lihaoyi/scalatags
.. _Dejawu : http://github.com/frgomes/dejawu
.. _`Dojo Toolkit` : http://demos.dojotoolkit.org/demos/

  
Status
======

1. Code based on scalatags
2. Basic skeleton which explores the organization of the code
3. Just a few graphical and non-graphical components at the moment.

Next steps
==========

1. Employ code generation as much as possible as a first step.
   The code generator works, but it needs to be rewritten since scalatags 0.3.8
   introduced new ways of defining widgets. The code generator is disabled for
   the time being.


Medium term goals
=================
   
11. Provide more flexible data stores, with coherent data structures and type checking
12. Provide reactive data stores
13. Add a grid widget

Long term goals
===============

101. Create a single-page demo similar to http://demos.dojotoolkit.org/demos/themePreviewer/demo.html
102. Create a demo similar to http://demos.dojotoolkit.org/demos/
