lazy val cassidoo =
  project
    .in(file("."))
    .settings(
      name := "cassidoo",
      version := "0.1.0",
      organization := "assayire",
      scalaVersion := "3.3.0"
    )
    .settings(
      libraryDependencies ++=
        "org.typelevel"            %% "cats-core"                % "2.9.0" ::
          "org.scalatest"          %% "scalatest"                % "3.2.15"   % Test ::
          "org.scalatestplus"      %% "scalacheck-1-17"          % "3.2.15.0" % Test ::
          "org.scalacheck"         %% "scalacheck"               % "1.17.0"   % Test ::
          "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0" ::
          Nil
    )
