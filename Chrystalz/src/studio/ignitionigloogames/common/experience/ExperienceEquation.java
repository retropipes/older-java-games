package studio.ignitionigloogames.common.experience;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public final class ExperienceEquation extends Polynomial {
    // Fields
    private int maxRange;
    private boolean experience;

    // Constructors
    private ExperienceEquation(final Polynomial p) {
        super(p);
    }

    public ExperienceEquation(final int maxPower, final int params,
            final int range, final boolean isExperience) {
        super(maxPower, params);
        this.maxRange = range;
        this.experience = isExperience;
    }

    // Methods
    public int getMaxRange() {
        return this.maxRange;
    }

    public boolean isExperience() {
        return this.experience;
    }

    @Override
    public long evaluate(final int paramValue) {
        int x;
        long result = 0;
        for (x = 0; x < this.coefficients.length; x++) {
            result += (long) (this.coefficients[x][Polynomial.DEFAULT_PARAM]
                    * Math.pow(paramValue, x));
        }
        if (this.experience) {
            for (x = 0; x < this.coefficients.length; x++) {
                result -= (long) this.coefficients[x][Polynomial.DEFAULT_PARAM];
            }
        }
        return result;
    }

    @Override
    public long evaluate(final int[] paramValues) {
        int x, y;
        long result = 0;
        for (x = 0; x < this.coefficients.length; x++) {
            for (y = 0; y < this.coefficients[x].length; y++) {
                result += (long) (this.coefficients[x][y]
                        * Math.pow(paramValues[y], x));
            }
        }
        if (this.experience) {
            for (x = 0; x < this.coefficients.length; x++) {
                for (y = 0; y < this.coefficients[x].length; y++) {
                    result -= (long) this.coefficients[x][y];
                }
            }
        }
        return result;
    }

    public long[] evaluateToArray() {
        final long[] result = new long[this.maxRange];
        for (int x = 0; x < result.length; x++) {
            result[x] = this.evaluate(x + 1);
        }
        return result;
    }

    public static ExperienceEquation readPage(final FileIOReader reader)
            throws IOException {
        final ExperienceEquation p = new ExperienceEquation(
                Polynomial.readPolynomial(reader));
        final int tempMaxRange = reader.readInt();
        final boolean tempExperience = reader.readBoolean();
        p.maxRange = tempMaxRange;
        p.experience = tempExperience;
        return p;
    }

    public void writePage(final FileIOWriter writer) throws IOException {
        this.writePolynomial(writer);
        writer.writeInt(this.maxRange);
        writer.writeBoolean(this.experience);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.experience ? 1231 : 1237);
        return prime * result + this.maxRange;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof ExperienceEquation)) {
            return false;
        }
        final ExperienceEquation other = (ExperienceEquation) obj;
        if (this.experience != other.experience) {
            return false;
        }
        if (this.maxRange != other.maxRange) {
            return false;
        }
        return true;
    }
}
