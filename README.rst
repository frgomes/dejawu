*dejawu* provides Dojo Toolkit wrappers for ScalaJS with Scalatags


Quick guide for the impatient
=============================

For the time being, you will need to install hammered version of Scalatags:

::

   $ cd ~/workspace
   $ git clone http://github.com/frgomes/scalatags
   $ cd scalatags
   $ sbt clean update compile publish-local


Then you can try Dejawu   

::

    $ cd ~/workspace
    $ git clone http://github.com/frgomes/dejawu   
    $ sbt clean update compile run
    $ firefox http://localhost:9000/purejs # this is HTML code
    $ firefox http://localhost:9000/app    # this is Scala with ScalaJS+scalatags+dejawu


Note: internet connection is required for running the examples.
This requirement will be lifted in future in due course.

* The first page is written in pure Javascript, employing Dojo Toolkit behind the
  scenes. This page is doomed to dissapear in future.

* The second page is written entirely in Scala and it is intended to become a
  replacement of the page written in Javascript, providing the same look and feel
  of the first page. It employs Dojo Toolkit behind the scenes.


  
Status for the impatient
========================

Working on the stabilization of the short term goals:

* http://localhost:9000/app renders reasonably well, despite some issues still under investigation.
  
* translator HTML -> ScalaJS (with Scalatags + Dejawu) mostly works, but still requires more test cases and code review when complex HTML pages are converted.

* code generator is temporarily disabled


  
What *dejawu* is about?
=======================

Imagine you would like to write a single-page web application, which looks like a
regular desktop application, in the way GMail or Yahoo Mail look like.

Imagine you would like to employ a high level programming language, with strong type checking
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

  
Planning
========

Done
----

1. Code based on scalatags
2. Basic skeleton which explores the organization of the code
3. A fairly reasonable set of graphical and non-graphical components at the moment.
4. Translator HTML to Scala

   
Next steps
----------

1. More test cases for the translator. This may contribute to item 2 below.
2. Stabilize proof of concept implemented in class ScalaJSExample.scala
3. Employ code generation as much as possible as a first step.
   
Medium term goals
-----------------
   
11. Add a grid widget
12. Provide more flexible data stores, with coherent data structures and type checking
13. Provide reactive data stores

Long term goals
---------------

101. Create a single-page demo similar to http://demos.dojotoolkit.org/demos/themePreviewer/demo.html
102. Create some more demos inspiered by http://demos.dojotoolkit.org/demos/
