import Card from "../../components/UI/Card/Card";

import classes from "./PercentageBar.module.css";
// props: value, maxValue, title, label, backgroundColor
const PercentageBar = (props) => {
  let barFillWidth = "0%";

  if (props.maxValue > 0) {
    barFillWidth = Math.round((props.value / props.maxValue) * 100) + "%";
  }

  const formatMoney = (number) => new Intl.NumberFormat().format(number);

  return (
    <Card unset>
      <section className={classes["chart-bar"]}>
        <span className={classes.title}>{props.title}</span>
        <div className={classes["chart-bar__inner"]}>
          <div
            className={classes["chart-bar__fill"]}
            style={{
              width: barFillWidth,
              backgroundColor: props.fill,
            }}
          ></div>
        </div>
        <span className={classes.label}>
          {formatMoney(props.label)}
          <span className={classes.won}>원</span>
        </span>
      </section>
    </Card>
  );
};

export default PercentageBar;
