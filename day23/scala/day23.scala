//package aoc2018
import scala.collection.mutable

object day23 extends App {

  case class Point3(x: Int, y: Int, z: Int) {
    def manhattan(that: Point3): Int =
      (this.x - that.x).abs + (this.y - that.y).abs + (this.z - that.z).abs
  }
  case class Nanobot(location: Point3, radius: Int) {
    def canReach(p: Point3): Boolean = p.manhattan(location) <= radius
  }

  case class Box(center: Point3, radius: Int) {
    def isReachable(nanobot: Nanobot): Boolean =
      center.manhattan(nanobot.location) <= radius + nanobot.radius

    def reachableCount(nanobots: Iterable[Nanobot]): Int =
      nanobots.count(this.isReachable)

    def subBoxes: Iterable[Box] =
      for {
        x <- -1 to 1
        y <- -1 to 1
        z <- -1 to 1
        r = (radius + 1) / 3
      } yield {
        Box(Point3(center.x + x * r, center.y + y * r, center.z + z * r), r)
      }
  }

  def parseInput(input: String): List[Nanobot] = {
    val LineReg = "pos=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>, r=(-?[0-9]+)".r
    input.split("\n").toList.map {
      case LineReg(x, y, z, r) =>
        Nanobot(Point3(x.toInt, y.toInt, z.toInt), r.toInt)
    }
  }

  def part1(input: String): Int = {
    val nanobots = parseInput(input)
    val toConsider = nanobots.maxBy(_.radius)
    nanobots.map(_.location).count(toConsider.canReach)
  }

  def median(xs: Iterable[Int]): Int = xs.min + (xs.max - xs.min) / 2

  def part2(input: String): Int = {
    val nanobots = parseInput(input)
    findWithLargestReachable(nanobots).manhattan(Point3(0, 0, 0))
  }

  private def findWithLargestReachable(nanobots: List[Nanobot]) = {
    val initial: Box = initialBox(nanobots)
    val queue = mutable.PriorityQueue(
      (initial, initial.reachableCount(nanobots))
    )(Ordering.by(_._2))
    def loop(): Point3 = {
      queue.dequeue match {
        case (box, _) if box.radius == 0 =>
          box.center
        case (box, _) =>
          for {
            b <- box.subBoxes
          } {
            queue.enqueue((b, b.reachableCount(nanobots)))
          }
          loop()
      }
    }
    loop()
  }

  private def initialBox(nanobots: List[Nanobot]) = {
    val xs = nanobots.map(_.location.x)
    val ys = nanobots.map(_.location.y)
    val zs = nanobots.map(_.location.z)
    val center = Point3(median(xs), median(ys), median(zs))
    Box(center, nanobots.map(_.location.manhattan(center)).max)
  }

  val input = io.Source.stdin.getLines.mkString("\n")
  println("part1 = " + part1(input))
  println("part2 = " + part2(input))
}
