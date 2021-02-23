/**
 * The NBody to simulate the N-Body problem.
 * @author   Lenard Zhang
 */
public class NBody
{

    public static double readRadius(String fileName)
    {
        In input = new In(fileName);
        int temp = input.readInt();
        return input.readDouble();
    }

    public static Planet[] readPlanets(String fileName)
    {
        In input = new In(fileName);
        int numPlanet = input.readInt();
        Planet[] planets = new Planet[numPlanet];
        double[] temp = new double[5];
        temp[0] = input.readDouble();
        String img = null;
        for(int i = 0; i < numPlanet; i++)
        {
            for(int j = 0; j < 5; j++)
                temp[j] = input.readDouble();
            img = input.readString();
            planets[i] = new Planet(temp[0], temp[1], temp[2], temp[3] ,temp[4], img);
        }
        return planets;
    }

    public static void main(String[] args)
    {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for(Planet p : planets)
            p.draw();
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];
        StdDraw.enableDoubleBuffering();
        for(double t = 0.0; t <= T; t += dt)
        {
            for(int i = 0; i < planets.length; i++)
            {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0; i < planets.length; i++)
                planets[i].update(dt, xForces[i], yForces[i]);
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet p : planets)
                p.draw();
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for(int i = 0; i < planets.length; i++)
        {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

}