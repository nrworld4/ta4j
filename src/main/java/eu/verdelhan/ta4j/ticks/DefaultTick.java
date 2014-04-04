/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marc de Verdelhan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.verdelhan.ta4j.ticks;


import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;

/**
 * A default tick period.
 * <p>
 */
public class DefaultTick implements Tick {

    private DateTime beginTime;

    private DateTime endTime;

    private double openPrice = -1;

    private double closePrice = -1;

    private double maxPrice = -1;

    private double minPrice = -1;

    private double amount = 0d;

    private double volume = 0d;

    private int trades = 0;

    /**
     * @param beginTime the begin time of the tick period
     * @param endTime the end time of the tick period
     */
    public DefaultTick(DateTime beginTime, DateTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    /**
     * @param endTime the end time of the tick period
     * @param openPrice the open price of the tick period
     * @param highPrice the highest price of the tick period
     * @param lowPrice the lowest price of the tick period
     * @param closePrice the close price of the tick period
     * @param volume the volume of the tick period
     */
    public DefaultTick(DateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        this.endTime = endTime;
        this.openPrice = openPrice;
        this.maxPrice = highPrice;
        this.minPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }

    @Override
    public double getClosePrice() {
        return closePrice;
    }

    @Override
    public double getOpenPrice() {
        return openPrice;
    }

    @Override
    public int getTrades() {
        return trades;
    }

    @Override
    public double getMaxPrice() {
        return maxPrice;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    /**
     * Adds a trade at the end of tick period.
     * @param tradeAmount the tradable amount
     * @param tradePrice the price
     */
    public void addTrade(double tradeAmount, double tradePrice) {
        if (openPrice < 0) {
            openPrice = tradePrice;
        }
        closePrice = tradePrice;

        if (maxPrice < 0) {
            maxPrice = tradePrice;
        } else {
            maxPrice = (maxPrice < tradePrice) ? tradePrice : maxPrice;
        }
        if (minPrice < 0) {
            minPrice = tradePrice;
        } else {
            minPrice = (minPrice > tradePrice) ? tradePrice : minPrice;
        }
        amount += tradeAmount;
        volume += tradeAmount * tradePrice;
        trades++;
    }

    @Override
    public double getMinPrice() {
        return minPrice;
    }

    @Override
    public DateTime getBeginTime() {
        return beginTime;
    }

    @Override
    public DateTime getEndTime() {
        return endTime;
    }

    public boolean inPeriod(DateTime timestamp) {
        return timestamp == null ? false : (!timestamp.isBefore(beginTime) && timestamp.isBefore(endTime));
    }

    @Override
    public String toString() {
        return String.format("[time: %1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS, close price: %2$f]", endTime
                .toGregorianCalendar(), closePrice);
    }

    @Override
    public String getDateName() {
        return endTime.toString("hh:mm dd/MM/yyyy");
    }

    @Override
    public String getSimpleDateName() {
        return endTime.toString("dd/MM/yyyy");
    }
}