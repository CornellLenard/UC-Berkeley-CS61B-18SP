/**
 * The Planet to model a planet in the universe.
 * @author   Lenard Zhang
 */
public class Planet
{

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double gConst = 6.67E-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img)
    {
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    public Planet(Planet p)
    {
        xxPos=p.xxPos;
        yyPos=p.yyPos;
        xxVel=p.xxVel;
        yyVel=p.yyVel;
        mass=p.mass;
        imgFileName=p.imgFileName;
    }

    public double calcDistance(Planet p)
    {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p)
    {
        return gConst * mass * p.mass / (calcDistance(p) * calcDistance((p)));
    }

    public double calcForceExertedByX(Planet p)
    {
        return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p)
    {
        return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] planets)
    {
        double totalForce = 0.0;
        for(Planet p : planets)
        {
            if(this.equals(p) == true)
                continue;
            totalForce += calcForceExertedByX(p);
        }
        return totalForce;
    }

    public double calcNetForceExertedByY(Planet[] planets)
    {
        double totalForce = 0.0;
        for(Planet p : planets)
        {
            if(this.equals(p) == true)
                continue;
            totalForce += calcForceExertedByY(p);
        }
        return totalForce;
    }

    public void update(double dt, double xForce, double yForce)
    {
        double xAcc = xForce / mass;
        double yAcc = yForce / mass;
        xxVel += dt * xAcc;
        yyVel += dt * yAcc;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw()
    {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

}