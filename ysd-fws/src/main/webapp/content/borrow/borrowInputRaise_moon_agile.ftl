						<tbody>
							<tr><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">业务类型：</td>
								<td style="">
									<select id="businessType" name="borrow.businessType" onchange="showContractInfo()" class="kaqu_w101">
<#if btp==7>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="12" <#if raiseTypeCode=='12'>selected </#if> >奢品贷</option>
<#elseif btp==8>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="03" <#if raiseTypeCode=='03'>selected</#if> >动产</option>
										<option value="04" <#if raiseTypeCode=='04'>selected</#if> >保证</option>
										<option value="13" <#if raiseTypeCode == '13'>selected </#if> >益老贷</option>
										<option value="14" <#if raiseTypeCode == '14'>selected </#if> >券贷通</option>
										<option value="15" <#if raiseTypeCode == '15'>selected</#if> >转贷宝</option>
<#elseif btp==9>
										<option value="11" <#if raiseTypeCode=='11'>selected</#if> >信用</option>
<#elseif btp==10>
										<option value="99" <#if raiseTypeCode=='99'>selected</#if> >体验</option>
<#elseif btp==11>
										<option value="06" <#if raiseTypeCode == '06'>selected </#if>  >灵活宝（企业）</option>
										<option value="07" <#if raiseTypeCode == '07'>selected </#if>  >灵活宝（个人）</option>
</#if>
									</select>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">法定代表人：</td>
								<td style="">
									<input type="text" value=""  name ="legalPerson" id="contractMove_legalPerson" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">住所地址：</td>
								<td style="">
									<input type="text" value="" name ="address" id="contractMove_address" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">联系电话：</td>
								<td style="">
									<input type="text" value="" name ="telphone" id="contractMove_telphone" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">借款用途：</td>
								<td style="">
									<input type="text" value="" name ="purpose" id="contractMove_purpose" class="input2 w_252"/>
								</td>
							</tr>
							
							
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">受托人：</td>
								<td style="">
									<input type="text" value="" name ="consignee" id="contractMove_consignee" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">受托人身份证：</td>
								<td style="">
									<input type="text" value="" name ="consigneeCardId" id="contractMove_consigneeCardId" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">居间介绍方：</td>
								<td style="">
									<input type="text" value="" name ="introduce" id="contractMove_introduce" class="input2 w_252"/>
								</td>
							</tr>
							
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">还款来源：</td>
								<td style="">
									<input type="text" value="" name ="payment" id="contractMove_payment" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">合同签约地：</td>
								<td style="">
									<input type="text" value="" name ="contractSigned" id="contractMove_contractSigned" class="input2 w_252"/>
								</td>
							</tr>
							
						</tbody>
					
     
