package org.actech.smart.trader.sync.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by paul on 2018/3/13.
 */
@Document
@NoArgsConstructor
public class StockFundTrack extends Signature {

}
