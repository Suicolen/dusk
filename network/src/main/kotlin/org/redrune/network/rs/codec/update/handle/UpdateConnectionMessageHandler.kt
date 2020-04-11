package org.redrune.network.rs.codec.update.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import org.redrune.core.network.codec.message.MessageHandler
import org.redrune.core.network.model.session.getSession
import org.redrune.network.rs.codec.update.decode.UpdateConnectionMessage
import org.redrune.network.rs.codec.update.encode.UpdateRegistryResponse
import org.redrune.utility.constants.network.LoginResponseCodes

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
class UpdateConnectionMessageHandler : MessageHandler<UpdateConnectionMessage>() {

    private val logger = InlineLogger()

    override fun handle(ctx: ChannelHandlerContext, msg: UpdateConnectionMessage) {
        if (msg.value != 3) {
            ctx.writeAndFlush(UpdateRegistryResponse(LoginResponseCodes.BAD_SESSION_ID))
            logger.warn { "Invalid connection id ${ctx.channel().getSession().getIp()} ${msg.value}" }
            return
        }
        logger.info { "Connection complete ${ctx.channel().getSession().getIp()}" }
    }
}